package com.proveedor.funcionalidad.webservices;

import java.io.IOException;
import java.util.ArrayList;

/////////////////////

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.proveedor.core.ProductoRptaDTO;
import com.proveedor.core.RespuestaProductoAltaDTO;
import com.proveedor.core.RespuestaProductoDTO;
import com.proveedor.core.RespuestaProductoConsultaDTO;
import com.proveedor.funcionalidad.submodulo.aes.AesEncrypDecryp;
import com.proveedor.funcionalidad.utils.CertificateUtil;
import com.proveedor.funcionalidad.utils.ConfigMap;
import com.proveedor.funcionalidad.utils.Logger;
import com.proveedor.funcionalidad.utils.APPException;
import okhttp3.Response;

/**
 * webservice de producto
 * 
 * @author GGCH
 *
 */

@WebService(name = "ProductosWS", portName = "ProductosWSPort", serviceName = "ProductosWSService", targetNamespace = "http://localhost/ProductosWS", endpointInterface = "com.proveedor.funcionalidad.webservices.ProductoWS")
public class ProductoWS {

	@Resource
	private javax.xml.ws.WebServiceContext wsContext;

	private static final Logger LOG = Logger.getLogger(ProductoWS.class.getName());
	ConfigMap config = new ConfigMap();

	private String requestBodyFinal;
	private String requestBody;
	private String dataEncript;
	private String jsonData;
	private String jsonDataFinal;
	private Response response;
	private String constResult = "Result";
	private String cierreComentario = " **********";
	private String constError = "ERROR";
	private String constStatus = "status";
	private String constStatusMay = "Status";
	private String constDescription = "description";
	private String constResponsePartyReferenceDataDirectory = "ResponsePartyReferenceDataDirectory";
	private String constMsgErrorProductoConsulta = "Error en la ejecución del microservicio consulta producto::";
	private String constMsgErrorProductoAlta = "Error en la ejecución del microservicio alta producto::";
	private String constMsgErrorProductoModificacion = "Error en la ejecución del microservicio modificación producto::";
	private String constMsgErrorProductoBaja = "Error en la ejecución del microservicio baja de producto::";
	private String constCanonicalError = "CanonicalError";
	private String constResponseReference = "responseReference";
	private String constResponseDescription = "responseDescription";
	private String constCode = "code";
	private String log;

	/**
	 * constructor de la clase que carga los parámetros usados en la aplicación
	 * 
	 * @throws APPException
	 */
	public ProductoWS() throws APPException {
		// this.config.loadVariables();
	}

	@WebMethod
	@WebResult(name = "respuesta")
	public RespuestaProductoAltaDTO alta(
			@WebParam(name = "id") int id,
			@WebParam(name = "nombre") String nombre,
			@WebParam(name = "codigoBarra") String codigoBarra) {

		log = "[Alta:" + id + "-" + nombre + "-" + codigoBarra + "]";
		LOG.info(cierreComentario + "Iniciando Alta Producto: " + id + "-" + nombre
				+ cierreComentario);

		RespuestaProductoAltaDTO respuesta = new RespuestaProductoAltaDTO();

		try {
			requestBody = "\n{\n    \"RequestPartyReferenceDataDirectory\": {\n        \"Recipient\": {"
					+ "\n            \"id\": \"" + id
					+ "\",\n            \"nombre\": \"" + nombre
					+ "\",\n            \"codigoBarra\": \"" + codigoBarra
					+ "\"\n        }\n    }\n}\n\t\t";

			LOG.info(log + "Request Body Llamada Core Alta Desencriptado ::" + requestBody);

			dataEncript = AesEncrypDecryp.InvokeAesEncrypt(requestBody, this.config.getKeybytes(),
					this.config.getIvbytes());
			requestBodyFinal = creaRequestBodyFinal(dataEncript);

			LOG.info(log + "Request Body Llamada Core Alta Encriptado ::" + requestBodyFinal);

			response = CertificateUtil.getOkHttpClient(config)
					.newCall(CertificateUtil.getRequest(config, requestBodyFinal,
							this.config.getHttpAlta()))
					.execute();

			jsonData = response.body().string();
			LOG.info(log + "Respuesta Encriptada Core Alta :: " + jsonData);
			JSONArray data = getData(jsonData);
			ArrayList<String> listdata = retornaListData(data);

			jsonDataFinal = AesEncrypDecryp.InvokeAesDecrypt(listdata.toString(), this.config.getKeybytes(),
					this.config.getIvbytes());

			LOG.info(log + "Respuesta Desencriptada Core Alta :: " + jsonDataFinal);
			JSONObject result = getResult(jsonDataFinal);

			if (result.getString(constStatus).equalsIgnoreCase(constError)) {
				JSONObject canonicalError = result.getJSONObject(constCanonicalError);
				respuesta.setCodigoRespuesta(canonicalError.getString(constCode));
				respuesta.setMensaje(canonicalError.getString(constDescription));
				respuesta.setCodigoProducto(0);
			} else if (result.getString(constStatus).equalsIgnoreCase("OK")) {
				JSONObject responsePartyReferenceDataDirectory = result
						.getJSONObject(constResponsePartyReferenceDataDirectory);
				JSONObject status = responsePartyReferenceDataDirectory.getJSONObject(constStatusMay);
				respuesta.setCodigoRespuesta(status.getString(constResponseReference));
				respuesta.setMensaje(status.getString(constResponseDescription));
				JSONObject recipient = responsePartyReferenceDataDirectory.getJSONObject("Recipient");
				if (recipient != null) {
					respuesta.setCodigoProducto(recipient.getInt("recipientReference"));
				}
			}
		} catch (JSONException e) {
			LOG.error(log + constMsgErrorProductoAlta + e.getMessage());
			respuesta.setMensaje(constMsgErrorProductoAlta + e.getMessage());
			respuesta.setCodigoRespuesta("0");
		} catch (IOException e) {
			LOG.error(log + constMsgErrorProductoAlta + e.getMessage());
			respuesta.setMensaje(constMsgErrorProductoAlta + e.getMessage());
			respuesta.setCodigoRespuesta("0");
		}

		LOG.info(cierreComentario + " Finalizando Alta Producto: " + id + "-" + nombre
				+ cierreComentario);

		return respuesta;
	}

	@WebMethod
	@WebResult(name = "respuesta")
	public RespuestaProductoConsultaDTO consulta(
			@WebParam(name = "id") int id) {

		log = "[Consulta:" + id + "]";
		LOG.info(cierreComentario + " Iniciando Consulta Producto: " + id + cierreComentario);

		RespuestaProductoConsultaDTO respuesta = new RespuestaProductoConsultaDTO();

		try {
			requestBody = "{\"RequestPartyReferenceDataDirectory\":{"
					+ "\"Recipient\":{\"id\":\"" + id
					+ "\"}}}";

			LOG.info(log + "Request Body Llamada Core Consulta Desencriptado ::" + requestBody);

			dataEncript = AesEncrypDecryp.InvokeAesEncrypt(requestBody, this.config.getKeybytes(),
					this.config.getIvbytes());
			requestBodyFinal = creaRequestBodyFinal(dataEncript);

			LOG.info(log + "Request Body Llamada Core Consulta Encriptada ::" + requestBodyFinal);

			response = CertificateUtil.getOkHttpClient(config)
					.newCall(CertificateUtil.getRequest(config, requestBodyFinal,
							this.config.getHttpConsulta()))
					.execute();

			jsonData = response.body().string();
			LOG.info(log + "Respuesta Encriptada Core Consulta :: " + jsonData);
			JSONArray data = getData(jsonData);
			ArrayList<String> listdata = retornaListData(data);

			jsonDataFinal = AesEncrypDecryp.InvokeAesDecrypt(listdata.toString(), this.config.getKeybytes(),
					this.config.getIvbytes());

			LOG.info(log + "Respuesta Desencriptada Core Consulta :: " + jsonDataFinal);
			JSONObject result = getResult(jsonDataFinal);

			if (result.getString(constStatus).equalsIgnoreCase(constError)) {
				JSONObject canonicalError = result.getJSONObject(constCanonicalError);
				respuesta.setCodigoRespuesta(canonicalError.getString(constCode));
				respuesta.setMensaje(canonicalError.getString(constDescription));
			} else if (result.getString(constStatus).equalsIgnoreCase("OK")) {
				JSONObject responsePartyReferenceDataDirectory = result
						.getJSONObject(constResponsePartyReferenceDataDirectory);
				JSONObject status = responsePartyReferenceDataDirectory.getJSONObject(constStatusMay);

				respuesta.setCodigoRespuesta(status.getString(constResponseReference));
				respuesta.setMensaje(status.getString(constResponseDescription));

				JSONArray recipient = responsePartyReferenceDataDirectory.getJSONArray("Recipient");

				if (recipient != null) {
					ProductoRptaDTO[] listaProductos = new ProductoRptaDTO[recipient.length()];
					for (int i = 0; i < recipient.length(); i++) {
						JSONObject objRecipient = new JSONObject((String) recipient.get(i).toString());
						ProductoRptaDTO productoRptaDTO = new ProductoRptaDTO();
						productoRptaDTO.setId(objRecipient.getString("id"));
						productoRptaDTO.setCodigoBarra(objRecipient.getString("codigoBarra"));
						listaProductos[i] = productoRptaDTO;
					}
					respuesta.setListaProductos(listaProductos);
				}
			}
		} catch (JSONException e) {
			LOG.error(log + constMsgErrorProductoConsulta + e.getMessage());
			respuesta.setMensaje(constMsgErrorProductoConsulta + e.getMessage());
			respuesta.setCodigoRespuesta("0");
		} catch (IOException e) {
			LOG.error(log + constMsgErrorProductoConsulta + e.getMessage());
			respuesta.setMensaje(constMsgErrorProductoConsulta + e.getMessage());
			respuesta.setCodigoRespuesta("0");
		}

		LOG.info(cierreComentario + " Finalizando Consulta Producto: " + id + cierreComentario);
		return respuesta;
	}

	@WebMethod
	@WebResult(name = "respuestaEliminarProducto")
	public RespuestaProductoDTO baja(
			@WebParam(name = "id") int id) {

		log = "[Baja:" + id + "]";
		LOG.info(cierreComentario + " Iniciando Baja Producto: " + id + cierreComentario);

		RespuestaProductoDTO respuesta = new RespuestaProductoDTO();

		try {
			requestBody = "\n{\n  \"RequestPartyReferenceDataDirectory\":{\n    \"Request\":{\n      \"actionType\":\"BAJA\"\n    },\n    \"Recipient\":{"
					+ " \n      \"id\": \"" + id + "\"\n    }\n  }\n}\n\t\t";

			LOG.info(log + "Request Body Llamada Core Baja Desencriptado ::" + requestBody);

			dataEncript = AesEncrypDecryp.InvokeAesEncrypt(requestBody, this.config.getKeybytes(),
					this.config.getIvbytes());
			requestBodyFinal = creaRequestBodyFinal(dataEncript);

			LOG.info(log + "Request Body Llamada Core Baja Encriptado ::" + requestBodyFinal);

			response = CertificateUtil.getOkHttpClient(config)
					.newCall(CertificateUtil.getRequest(config, requestBodyFinal,
							this.config.getHttpModificacion()))
					.execute();

			jsonData = response.body().string();
			LOG.info(log + "Respuesta Encriptada Core Baja :: " + jsonData);
			JSONArray data = getData(jsonData);
			ArrayList<String> listdata = retornaListData(data);

			jsonDataFinal = AesEncrypDecryp.InvokeAesDecrypt(listdata.toString(), this.config.getKeybytes(),
					this.config.getIvbytes());

			LOG.info(log + "Respuesta Desencriptada Core Baja :: " + jsonDataFinal);
			JSONObject result = getResult(jsonDataFinal);

			if (result.getString(constStatus).equalsIgnoreCase(constError)) {
				respuesta = respuestacanonicalError(result);
			} else if (result.getString(constStatus).equalsIgnoreCase("OK")) {
				JSONObject responsePartyReferenceDataDirectory = result
						.getJSONObject(constResponsePartyReferenceDataDirectory);
				JSONObject status = responsePartyReferenceDataDirectory.getJSONObject(constStatusMay);
				respuesta.setCodigoRespuesta(status.getString(constResponseReference));
				respuesta.setMensaje(status.getString(constResponseDescription));
			}
		} catch (JSONException e) {
			LOG.error(log + constMsgErrorProductoBaja + e.getMessage());
			respuesta.setMensaje(constMsgErrorProductoBaja + e.getMessage());
			respuesta.setCodigoRespuesta("0");
		} catch (IOException e) {
			LOG.error(log + constMsgErrorProductoBaja + e.getMessage());
			respuesta.setMensaje(constMsgErrorProductoBaja + e.getMessage());
			respuesta.setCodigoRespuesta("0");
		}
		LOG.info(cierreComentario + " Finalizando Baja Producto: " + id + cierreComentario);
		return respuesta;
	}

	@WebMethod
	@WebResult(name = "respuestaModificaProducto")
	public RespuestaProductoDTO modificacion(
			@WebParam(name = "id") int id,
			@WebParam(name = "nombre") String nombre,
			@WebParam(name = "codigoBarra") String codigoBarra) {

		log = "[Modificacion:" + id + "-" + nombre + "-" + codigoBarra + "]";
		LOG.info(cierreComentario + " Iniciando Modificacion Producto: " + id + cierreComentario);

		RespuestaProductoDTO respuesta = new RespuestaProductoDTO();

		try {
			requestBody = "\n{\n  \"RequestPartyReferenceDataDirectory\":{\n    \"Request\":{\n      \"actionType\":\"MODIFICACION\"\n    },\n    \"Recipient\":{ "
					+ "  \n      \"id\": \"" + id
					+ "\",\n      \"nombre\": \"" + nombre
					+ "\",\n      \"codigoBarra\": \"" + codigoBarra
					+ "\"\n    }\n  }\n}\n\t\t";

			LOG.info(log + "Request Body Llamada Core Modificacion Desencriptado ::" + requestBody);

			dataEncript = AesEncrypDecryp.InvokeAesEncrypt(requestBody, this.config.getKeybytes(),
					this.config.getIvbytes());
			requestBodyFinal = creaRequestBodyFinal(dataEncript);

			LOG.info(log + "Request Body Llamada Core Modificacion Encriptado ::" + requestBodyFinal);

			response = CertificateUtil.getOkHttpClient(config)
					.newCall(CertificateUtil.getRequest(config, requestBodyFinal,
							this.config.getHttpModificacion()))
					.execute();

			jsonData = response.body().string();
			LOG.info(log + "Respuesta Encriptada Core Modificacion ::" + jsonData);
			JSONArray data = getData(jsonData);
			ArrayList<String> listdata = retornaListData(data);

			jsonDataFinal = AesEncrypDecryp.InvokeAesDecrypt(listdata.toString(), this.config.getKeybytes(),
					this.config.getIvbytes());

			LOG.info(log + "Respuesta Desencriptada Core Modificacion :: " + jsonDataFinal);
			JSONObject result = getResult(jsonDataFinal);

			if (result.getString(constStatus).equalsIgnoreCase(constError)) {
				respuesta = respuestacanonicalError(result);
			} else if (result.getString(constStatus).equalsIgnoreCase("OK")) {
				JSONObject responsePartyReferenceDataDirectory = result
						.getJSONObject(constResponsePartyReferenceDataDirectory);
				JSONObject status = responsePartyReferenceDataDirectory.getJSONObject(constStatusMay);
				respuesta.setCodigoRespuesta(status.getString(constResponseReference));
				respuesta.setMensaje(status.getString(constResponseDescription));
			}
		} catch (JSONException e) {
			LOG.error(log + constMsgErrorProductoModificacion + e.getMessage());
			respuesta.setMensaje(constMsgErrorProductoModificacion + e.getMessage());
			respuesta.setCodigoRespuesta("0");
		} catch (IOException e) {
			LOG.error(log + constMsgErrorProductoModificacion + e.getMessage());
			respuesta.setMensaje(constMsgErrorProductoModificacion + e.getMessage());
			respuesta.setCodigoRespuesta("0");
		}

		LOG.info(cierreComentario + " Finalizando Modificacion Producto: " + id + cierreComentario);

		return respuesta;

	}

	private ArrayList<String> retornaListData(JSONArray data) {
		ArrayList<String> listdata = new ArrayList<>();
		try {
			if (data != null) {
				for (int i = 0; i < data.length(); i++) {
					listdata.add((String) data.get(i));
				}
			}
		} catch (JSONException e) {
			LOG.error("Error en funcion retornaListData ::" + e.getMessage());
		}
		return listdata;
	}

	private String creaRequestBodyFinal(String dataEncript) {
		return "{\"encryptedPayload\": {\"data\": [\"" + dataEncript + "\"]}}";
	}

	private JSONObject getResult(String jsonDataFinal) {
		JSONObject respuesta = new JSONObject();
		try {
			JSONObject object = new JSONObject(jsonDataFinal);
			respuesta = object.getJSONObject(constResult);
		} catch (JSONException e) {
			LOG.error("Error en funcion getResult ::" + e.getMessage());
		}
		return respuesta;
	}

	private JSONArray getData(String jsonData) {
		JSONArray respuesta = new JSONArray();
		try {
			JSONObject jobject = new JSONObject(jsonData);
			JSONObject encryptedPayload = jobject.getJSONObject("encryptedPayload");
			respuesta = encryptedPayload.getJSONArray("data");
		} catch (JSONException e) {
			LOG.error("Error en funcion getData ::" + e.getMessage());
		}
		return respuesta;
	}

	private RespuestaProductoDTO respuestacanonicalError(JSONObject result) {
		RespuestaProductoDTO respuesta = new RespuestaProductoDTO();
		try {
			JSONObject canonicalError = result.getJSONObject(constCanonicalError);
			respuesta.setCodigoRespuesta(canonicalError.getString(constCode));
			respuesta.setMensaje(canonicalError.getString(constDescription));
		} catch (JSONException e) {
			LOG.error("Error en funcion respuestacanonicalError ::" + e.getMessage());
		}
		return respuesta;
	}
}

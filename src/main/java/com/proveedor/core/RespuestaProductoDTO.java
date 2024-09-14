package com.proveedor.core;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author GGCH
 *
 */

@XmlType(name = "rptaProductoDTO")
@XmlRootElement
public class RespuestaProductoDTO {

	private String codigoRespuesta;
	private String mensaje;

	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}

	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}

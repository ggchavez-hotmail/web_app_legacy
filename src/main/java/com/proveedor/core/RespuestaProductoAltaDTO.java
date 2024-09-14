package com.proveedor.core;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author GGCH
 *
 */

@XmlType(name = "rptaAltaBenefDTO")
@XmlRootElement
public class RespuestaProductoAltaDTO {

	private String codigoRespuesta;
	private String mensaje;
	private int codigoProducto;

	/**
	 * 
	 * @return
	 */
	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}

	/**
	 * 
	 * @param codigoRespuesta
	 */
	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	/**
	 * 
	 * @return
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * 
	 * @param mensaje
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * 
	 * @return
	 */
	public int getCodigoProducto() {
		return codigoProducto;
	}

	/**
	 * 
	 * @param codigoProducto
	 */
	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

}

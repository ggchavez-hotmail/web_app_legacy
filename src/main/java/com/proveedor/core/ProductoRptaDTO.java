package com.proveedor.core;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * clase de respuesta dto producto
 * 
 * @author GGCH
 *
 */
@XmlRootElement
public class ProductoRptaDTO {

	private String id;
	private String codigoBarra;

	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getCodigoBarra() {
		return codigoBarra;
	}

	/**
	 * 
	 * @param codigoBarra
	 */
	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

}

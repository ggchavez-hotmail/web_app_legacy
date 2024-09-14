package com.proveedor.core;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * clase dto de producto
 * 
 * @author GGCH
 *
 */
@XmlRootElement
public class ProductoDTO {

	private String id;
	private String nombre;
	private String codigoBarra;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	/**
	 * java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductoDTO="
				+ "{ id=" + id
				+ ", nombre=" + nombre
				+ ", codigoBarra=" + codigoBarra
				+ "}";
	}

}

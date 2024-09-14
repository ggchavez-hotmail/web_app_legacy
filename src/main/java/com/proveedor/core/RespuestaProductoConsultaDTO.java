package com.proveedor.core;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author GGCH
 *
 */

@XmlType(name = "rptaConsBenefDTO")
@XmlRootElement
public class RespuestaProductoConsultaDTO extends RespuestaProductoDTO {

	private ProductoRptaDTO[] listaProductos;

	public ProductoRptaDTO[] getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(ProductoRptaDTO[] listaProductos) {
		this.listaProductos = listaProductos;
	}

}

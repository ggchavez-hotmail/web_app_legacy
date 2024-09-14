package com.proveedor.funcionalidad.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Centralización de las excepciones de la aplicación
 * almacenando codigo, mensaje y la excepción en so.
 * 
 * @author GGCH
 * @version 1.0
 */

public class APPException extends Exception {

	private static final long serialVersionUID = -8893537167359328770L;
	private final int codigo;
	private final String mensaje;
	private final Throwable e;

	/**
	 * Disponibiliza el codigo personalizado y la excepción.
	 * para trabajarlo en la clase.
	 * 
	 * @param codigo Codigo a disponibilizar.
	 * @param e      Excepción a disponibilizar.
	 */

	public APPException(int codigo, Throwable e) {
		super();
		this.codigo = codigo;
		this.e = e;
		this.mensaje = e.getMessage();
	}

	/**
	 * Se obtiene el código de error.
	 * 
	 * @return El código de error.
	 */

	public int getCodigoError() {
		return this.codigo;
	}

	/**
	 * Se obtiene el mensaje de error.
	 * 
	 * @return El mensaje de error.
	 */

	public String getMensajeError() {
		return this.mensaje;
	}

	/**
	 * Se obtiene la traza completa del error.
	 * Muy util cuando no se sabe la causa exacta.
	 * 
	 * @return La traza completa formateada para buena legibilidad.
	 */

	public String getTrazaError() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		this.e.printStackTrace(pw);
		return sw.toString();
	}

}

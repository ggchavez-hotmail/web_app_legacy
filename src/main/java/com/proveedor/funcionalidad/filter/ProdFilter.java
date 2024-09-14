package com.proveedor.funcionalidad.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.proveedor.funcionalidad.utils.ConfigMap;

public class ProdFilter implements javax.servlet.Filter {

	ConfigMap config = new ConfigMap();

	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		GenericResponseWrapper wrapper = new GenericResponseWrapper((HttpServletResponse) response);
		config.setVariables();

		filterChain.doFilter(request, wrapper);
		OutputStream out = response.getOutputStream();
		String wsdl = new String(wrapper.getData(), StandardCharsets.UTF_8);
		// con esto se realiza el cambio del end-point
		// wsdl = wsdl.replace(":80", "");
		// wsdl = wsdl.replace("localhost80", "localhost:8080");
		// Esto deberia esta comentado
		wsdl = wsdl.replace(this.config.getWsdlLocationOrigen(),
				this.config.getWsdlLocationDestino())
				.replace(this.config.getWsdlLocationOrigenCluster(),
						this.config.getWsdlLocationDestino());
		byte[] byteArrray = wsdl.getBytes();

		out.write(byteArrray);
		out.close();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// metodo sin implementacion

	}

	public void destroy() {
		// metodo sin implementacion

	}
}

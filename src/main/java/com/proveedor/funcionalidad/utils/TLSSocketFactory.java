package com.proveedor.funcionalidad.utils;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TLSSocketFactory extends SSLSocketFactory {
    private final SSLSocketFactory delegate;
    private static String strTLS = "TLSv1.3";

    public TLSSocketFactory(SSLSocketFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        SSLSocket sslSocket = (SSLSocket) delegate.createSocket(socket, host, port, autoClose);
        sslSocket.setEnabledProtocols(new String[] { strTLS });
        return sslSocket;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        SSLSocket sslSocket = (SSLSocket) delegate.createSocket(host, port);
        sslSocket.setEnabledProtocols(new String[] { strTLS });
        return sslSocket;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort) throws IOException {
        SSLSocket sslSocket = (SSLSocket) delegate.createSocket(host, port, localAddress, localPort);
        sslSocket.setEnabledProtocols(new String[] { strTLS });
        return sslSocket;
    }

    @Override
    public Socket createSocket(InetAddress address, int port) throws IOException {
        SSLSocket sslSocket = (SSLSocket) delegate.createSocket(address, port);
        sslSocket.setEnabledProtocols(new String[] { strTLS });
        return sslSocket;
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
            throws IOException {
        SSLSocket sslSocket = (SSLSocket) delegate.createSocket(address, port, localAddress, localPort);
        sslSocket.setEnabledProtocols(new String[] { strTLS });
        return sslSocket;
    }
}

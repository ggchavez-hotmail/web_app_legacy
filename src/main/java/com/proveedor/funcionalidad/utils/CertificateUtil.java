package com.proveedor.funcionalidad.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;

public class CertificateUtil {
    private static final Logger LOG = Logger.getLogger(CertificateUtil.class.getName());
    private static ConfigMap configMap;

    private CertificateUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static String formateaCertificado(String certificate) {
        return configMap.getBeginCertificate() +
                certificate.replace("\\\\n", "").replace(configMap.getBeginCertificate(), "")
                        .replace(configMap.getEndCertificate(), "")
                +
                configMap.getEndCertificate();
    }

    private static String eliminaHeaderFooterCertificadoKey(String certificate) {
        return certificate.replace("\\\\n", "").replace(configMap.getBeginRsaPrivateKey(), "")
                .replace(configMap.getEndRsaPrivateKey(), "");
    }

    private static X509Certificate convertToX509Certificate(String certificate) {
        X509Certificate cert = null;
        try {
            String newString = formateaCertificado(certificate);
            StringReader stringReader = new StringReader(newString);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int data;
            while ((data = stringReader.read()) != -1) {
                baos.write(data);
            }

            ByteArrayInputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
            cert = x509Certificate;
        } catch (Exception e) {
            LOG.error("Error en convertToX509Certificate::" + e.getMessage());
        }
        return cert;
    }

    public static OkHttpClient getOkHttpClient(ConfigMap config) {
        OkHttpClient client = null;
        configMap = config;

        try {
            Security.addProvider(new BouncyCastleProvider());

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            X509Certificate caCert = convertToX509Certificate(config.getCaCrt());
            keyStore.setCertificateEntry("ca-cert", caCert);

            X509Certificate cert = convertToX509Certificate(config.getCertificateCrt());
            String certificadoKeySinHeaderFooter = eliminaHeaderFooterCertificadoKey(config.getCertificateKey());

            byte[] keyBytes = Base64.getMimeDecoder().decode(certificadoKeySinHeaderFooter);

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(spec);

            // Agregar la clave privada y el certificado al KeyStore
            keyStore.setKeyEntry("server", privateKey, config.getPwdKeyStore().toCharArray(),
                    new Certificate[] { cert });

            // Crear un TrustManagerFactory y un SSLContext a partir del KeyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLSv1.3");

            TrustManager[] trustAllCerts = trustManagerFactory.getTrustManagers();
            if (trustAllCerts.length == 0) {
                throw new IllegalStateException("No TrustManager found");
            }

            sslContext.init(null, trustAllCerts, new SecureRandom());

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            ConnectionSpec connSpec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_3)
                    .cipherSuites(CipherSuite.TLS_AES_128_GCM_SHA256)
                    .build();

            Security.insertProviderAt(new BouncyCastleProvider(), 1);
            client = new OkHttpClient.Builder()
                    .sslSocketFactory(new TLSSocketFactory(sslContext.getSocketFactory()),
                            (X509TrustManager) trustAllCerts[0])
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .connectionSpecs(Collections.singletonList(connSpec))
                    .hostnameVerifier((hostname, session) -> hostname.contains("web-app-space.svc.cluster.local"))
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(logging)
                    .addInterceptor(logging)
                    .build();
        } catch (Exception e) {
            LOG.error("Error en getOkHttpClient::" + e.getMessage());
        }
        return client;
    }

    public static Request getRequest(ConfigMap config, String requestBody, String url) {
        configMap = config;
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Headers headers = new Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "*/*")
                .add("Host", "http://" + configMap.getWsdlLocationOrigenCluster())
                .add("Accept-Encoding", "gzip, deflate, br")
                .add("Connection", "keep-alive")
                .build();

        return new Request.Builder()
                .url(url)
                .method("POST", body)
                .headers(headers)
                .build();
    }
}
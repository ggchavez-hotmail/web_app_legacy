package com.proveedor.funcionalidad.utils;

public class ConfigMap {

    private static final Logger LOG = Logger.getLogger(ConfigMap.class.getName());

    private static final String version = "0.9.2 Version";

    /**
     * beginCertificate
     */
    private String beginCertificate;

    /**
     * endCertificate
     */
    private String endCertificate;

    /**
     * beginRsaPrivateKey
     */
    private String beginRsaPrivateKey;

    /**
     * endRsaPrivateKey
     */
    private String endRsaPrivateKey;

    /**
     * pwdKeyStore
     */
    private String pwdKeyStore;

    /**
     * caCrt
     */
    private String caCrt;

    /**
     * certificateKey
     */
    private String certificateKey;

    /**
     * certificateCrt
     */
    private String certificateCrt;

    /**
     * keybytes
     */
    private String keybytes;

    /**
     * ivbytes
     */
    private String ivbytes;

    /**
     * environment
     */
    private String environment;

    /**
     * httpNormal
     */
    private String httpConsulta;

    /**
     * httpChek
     */
    private String httpAlta;

    /**
     * httpRutero
     */
    private String httpModificacion;

    /**
     * WSNAME
     */
    private String wsName = "prodWebservice";

    /**
     * WSDLLOCATIONORIGEN
     */
    private String wsdlLocationOrigen = "localhost:8080";

    /**
     * WSDLLOCATIONORIGENCLUSTER
     */
    private String wsdlLocationOrigenCluster = "webapplegacy-service.web-app-space.svc.cluster.local:80";

    /**
     * WSDLLOCATIONDESTINO
     */
    private String wsdlLocationDestino = "webapp-dev.dominio.com";

    public String getVersion() {
        return version;
    }

    public String getWsName() {
        return wsName;
    }

    public void setWsName(String wsName) {
        this.wsName = wsName;
    }

    public String getWsdlLocationOrigen() {
        return wsdlLocationOrigen;
    }

    public void setWsdlLocationOrigen(String wsdlLocationOrigen) {
        this.wsdlLocationOrigen = wsdlLocationOrigen;
    }

    public String getWsdlLocationOrigenCluster() {
        return wsdlLocationOrigenCluster;
    }

    public void setWsdlLocationOrigenCluster(String wsdlLocationOrigenCluster) {
        this.wsdlLocationOrigenCluster = wsdlLocationOrigenCluster;
    }

    public String getWsdlLocationDestino() {
        return wsdlLocationDestino;
    }

    public void setWsdlLocationDestino(String wsdlLocationDestino) {
        this.wsdlLocationDestino = wsdlLocationDestino;
    }

    public void setHttpConsulta(String httpConsulta) {
        this.httpConsulta = httpConsulta;
    }

    public void setHttpAlta(String httpAlta) {
        this.httpAlta = httpAlta;
    }

    public void setHttpModificacion(String httpModificacion) {
        this.httpModificacion = httpModificacion;
    }

    public String getHttpConsulta() {
        return httpConsulta;
    }

    public String getHttpAlta() {
        return httpAlta;
    }

    public String getHttpModificacion() {
        return httpModificacion;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getCaCrt() {
        return caCrt;
    }

    public void setCaCrt(String caCrt) {
        this.caCrt = caCrt;
    }

    public String getCertificateKey() {
        return certificateKey;
    }

    public void setCertificateKey(String certificateKey) {
        this.certificateKey = certificateKey;
    }

    public String getCertificateCrt() {
        return certificateCrt;
    }

    public void setCertificateCrt(String certificateCrt) {
        this.certificateCrt = certificateCrt;
    }

    public String getKeybytes() {
        return keybytes;
    }

    public void setKeybytes(String keybytes) {
        this.keybytes = keybytes;
    }

    public String getIvbytes() {
        return ivbytes;
    }

    public void setIvbytes(String ivbytes) {
        this.ivbytes = ivbytes;
    }

    public String getPwdKeyStore() {
        return pwdKeyStore;
    }

    public void setPwdKeyStore(String pwdKeyStore) {
        this.pwdKeyStore = pwdKeyStore;
    }

    public String getBeginCertificate() {
        return beginCertificate;
    }

    public void setBeginCertificate(String beginCertificate) {
        this.beginCertificate = beginCertificate;
    }

    public String getEndCertificate() {
        return endCertificate;
    }

    public void setEndCertificate(String endCertificate) {
        this.endCertificate = endCertificate;
    }

    public String getBeginRsaPrivateKey() {
        return beginRsaPrivateKey;
    }

    public void setBeginRsaPrivateKey(String beginRsaPrivateKey) {
        this.beginRsaPrivateKey = beginRsaPrivateKey;
    }

    public String getEndRsaPrivateKey() {
        return endRsaPrivateKey;
    }

    public void setEndRsaPrivateKey(String endRsaPrivateKey) {
        this.endRsaPrivateKey = endRsaPrivateKey;
    }

    public void setVariables() {
        this.setWsName(Env.getEnv("WSNAME"));
        this.setWsdlLocationDestino(Env.getEnv("HOSTNAME_BASE"));
        this.setWsdlLocationOrigenCluster(Env.getEnv("URI_POD"));
        this.setWsdlLocationOrigen(Env.getEnv("APP_HOST") + ":" + Env.getEnv("APP_PORT"));
        this.setHttpConsulta(Env.getEnv("HTTP_CONSULTA_PRODUCTO"));
        this.setHttpAlta(Env.getEnv("HTTP_ALTA_PRODUCTO"));
        this.setHttpModificacion(Env.getEnv("HTTP_MODIFICATION_PRODUCTO"));
        this.setEnvironment(Env.getEnv("ENV"));

        this.setCaCrt(Env.getEnv("CA_CRT"));
        this.setCertificateKey(Env.getEnv("CERTIFICATE_KEY"));
        this.setCertificateCrt(Env.getEnv("CERTIFICATE_CRT"));

        this.setKeybytes(Env.getEnv("KEYBYTES"));
        this.setIvbytes(Env.getEnv("IVBYTES"));

        this.setPwdKeyStore(Env.getEnv("PWD_KEYSTORE"));

        this.setBeginCertificate(Env.getEnv("BEGIN_CERTIFICATE"));
        this.setBeginRsaPrivateKey(Env.getEnv("BEGIN_RSA_PRIVATE_KEY"));
        this.setEndCertificate(Env.getEnv("END_CERTIFICATE"));
        this.setEndRsaPrivateKey(Env.getEnv("END_RSA_PRIVATE_KEY"));
    }

    public void loadVariables() {
        try {
            this.setVariables();

            LOG.info(
                    "Inicializando servicio webAppLegacy version " + version
                            + " , leyendo variables desde configmap.k8s...");
            LOG.info("wsName: " + this.wsName);
            LOG.info("wsdlLocationOrigen: " + this.wsdlLocationOrigen);
            LOG.info("wsdlLocationOrigenCluster: " + this.wsdlLocationOrigenCluster);
            LOG.info("wsdlLocationDestino: " + this.wsdlLocationDestino);
            LOG.info("httpConsulta: " + this.httpConsulta);
            LOG.info("httpAlta: " + this.httpAlta);
            LOG.info("httpModificacion: " + this.httpModificacion);
            LOG.info("environment: " + this.environment);
            LOG.info("CA_CRT: " + this.caCrt);
            LOG.info("CERTIFICATE_KEY: " + this.certificateKey);
            LOG.info("CERTIFICATE_CRT: " + this.certificateCrt);
            LOG.info("PWD_KEYSTORE:: " + this.pwdKeyStore);

            LOG.info("BEGIN_CERTIFICATE:: " + this.beginCertificate);
            LOG.info("BEGIN_RSA_PRIVATE_KEY:: " + this.beginRsaPrivateKey);
            LOG.info("END_CERTIFICATE:: " + this.endCertificate);
            LOG.info("END_RSA_PRIVATE_KEY:: " + this.endRsaPrivateKey);

        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }
}

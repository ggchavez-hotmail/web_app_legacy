package com.proveedor.funcionalidad.submodulo.aes;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.proveedor.funcionalidad.utils.ConfigMap;
import com.proveedor.funcionalidad.utils.Logger;

public class AesEncrypDecryp {

    private static final Logger LOG = Logger.getLogger(AesEncrypDecryp.class.getName());
    ConfigMap config = new ConfigMap();

    public AesEncrypDecryp() {
        this.config.loadVariables();
    }

    public static String InvokeAesEncrypt(String value, String keyBytes_, String ivBytes_) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes_.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes_.getBytes());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes()));
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return null;
    }

    public static String InvokeAesDecrypt(String strToDecrypt, String keyBytes_, String ivBytes_) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes_.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes_.getBytes());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decodedBytes = Base64.getMimeDecoder().decode(strToDecrypt);
            return new String(cipher.doFinal(decodedBytes));
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return null;
    }
}
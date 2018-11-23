package com.arcagile.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class Utility {

    static String fileName = "faee2d0e3beb41e1912923d7f70d83c1_18112018.json";
    static byte[] raw = fileName.getBytes();

    static SecureRandom rnd = new SecureRandom();

    static IvParameterSpec iv = new IvParameterSpec(rnd.generateSeed(32));

    private static final String key         = "faee2d0e3beb41e1912923d_19112018";
    //BELOW IS HARDCODED WHICH IS MANDATORILY REQUIRED TO 16bytes for CBC ENCRYPTION
    private static final String initVector  = "encryptionIntVec";

    public static void main(String[] args) throws  Exception {
        byte[] test = fileName.getBytes();
        System.out.println("File name bytes :  " +test);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","syed maqbul");
        jsonObject.put("age",12);
        jsonObject.put("city","Koppal");
        String originalString = jsonObject.toString();
        System.out.println("Original String to encrypt - " + originalString);
        String encryptedString = encryptText(originalString);
        System.out.println("Encrypted String - " + encryptedString);
        String decryptedString = decryptText(encryptedString);
        System.out.println("After decryption - " + decryptedString);
    }

    public static String encryptText(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decryptText(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}

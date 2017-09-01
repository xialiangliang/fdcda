package com.keyou.fdcda.api.utils;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.util.Base64;

/**
 * Created by zzq on 2017-08-27.
 */
public class EncodeUtil {
    
    public static String data="e10adc3949ba59abbe56e057f20f883e";
    
    //生成密钥对
    public static KeyPair rsaGenKeyPair() throws Exception{
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    //公钥加密  
    public static byte[] rsaEncrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher= Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    //私钥解密  
    public static byte[] rsaDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }
    
    //md5
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    //hash
    public static String hash(String str, String encode) {
        if ("md5".equalsIgnoreCase(encode)) {
            return md5(str);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair=rsaGenKeyPair();

        //获取公钥，并以base64格式打印出来  
        PublicKey publicKey=keyPair.getPublic();
        System.out.println("公钥："+new String(Base64.getEncoder().encode(publicKey.getEncoded())));

        //获取私钥，并以base64格式打印出来  
        PrivateKey privateKey=keyPair.getPrivate();
        System.out.println("私钥："+new String(Base64.getEncoder().encode(privateKey.getEncoded())));

        //公钥加密  
        byte[] encryptedBytes=rsaEncrypt(data.getBytes(), publicKey);
        System.out.println("加密后："+new String(Base64.getEncoder().encode(encryptedBytes)));

        //私钥解密  
        byte[] decryptedBytes=rsaDecrypt(encryptedBytes, privateKey);
        System.out.println("解密后："+new String(decryptedBytes));
    }
}

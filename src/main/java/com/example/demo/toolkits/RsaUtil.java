package com.example.demo.toolkits;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaUtil {
    public static String encryptPasswordRSA(String password, String rsaPublickey) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 将Base64编码的私钥和公钥解码
        byte[] decodedPublicKey = Base64.getDecoder().decode(rsaPublickey);
        // 创建公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(decodedPublicKey));
        // 初始化加密器
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 加密密码
        byte[] encryptedPasswordBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedPasswordBytes);
    }
}

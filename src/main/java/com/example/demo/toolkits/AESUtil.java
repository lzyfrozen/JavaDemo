package com.example.demo.toolkits;

// import cn.hutool.core.text.CharSequenceUtil;
// import com.bmw.blm.constant.DataSource;
// import com.bmw.blm.dc.battery.supplier.enums.ErrorCode;
// import com.bmw.blm.dc.battery.supplier.exceptions.CustomRuntimeException;
// import com.bmw.blm.mongo.DataMessage;
// import com.bmw.blm.dc.battery.supplier.threadlocal.MongoIdGenerator;
// import lombok.CustomLog;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class AESUtil {

    private AESUtil() {
    }

    public static String encrypt(String plainText, String secretKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            Key key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] iv = new byte[12]; // 96-bit IV for GCM mode
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv); // 128-bit authentication tag
            cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] cipherTextWithIV = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, cipherTextWithIV, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, cipherTextWithIV, iv.length, encryptedBytes.length);
            return Base64.getEncoder().encodeToString(cipherTextWithIV);
        } catch (Exception e) {
            return "";
        }
    }

    public static String decrypt(String encryptedText, String secretKey) {
        try {
            // String mongoId = MongoIdGenerator.getUniqueId();
            byte[] cipherTextWithIV = Base64.getDecoder().decode(encryptedText);
            byte[] iv = new byte[12]; // 96-bit IV for GCM mode
            byte[] encryptedBytes = new byte[cipherTextWithIV.length - iv.length];
            System.arraycopy(cipherTextWithIV, 0, iv, 0, iv.length);
            System.arraycopy(cipherTextWithIV, iv.length, encryptedBytes, 0, encryptedBytes.length);

            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            Key key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv); // 128-bit authentication tag
            cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);
            return decryptedData;
        } catch (Exception e) {
            return "";
        }
    }
}

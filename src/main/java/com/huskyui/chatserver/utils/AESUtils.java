package com.huskyui.chatserver.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtils {
    // 加密方法
    public static String encrypt(String data, String secretKey) {
        try {
            // 创建AES密码器
            Cipher cipher = Cipher.getInstance("AES");

            // 生成密钥
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");

            // 初始化加密模式
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            // 加密数据
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());

            // 转换为Base64字符串返回
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }catch (Exception e){
            return null;
        }
    }

    // 解密方法
    public static String decrypt(String encryptedData, String secretKey) throws Exception {
        // 创建AES密码器
        Cipher cipher = Cipher.getInstance("AES");

        // 生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");

        // 初始化解密模式
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        // 解密数据
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        return new String(decryptedBytes);
    }
}

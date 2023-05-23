package drain.api.controller;

import drain.api.provider.ShaProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class OpenSSLController extends ShaProvider {

    public static String decrypt_openssl_data(String data) throws Exception {
        String key = ShaProvider.lvv;

        String encryptedData = data;
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);

        byte[] iv = new byte[16];
        byte[] hmac = new byte[32];
        byte[] ciphertext = new byte[decodedData.length - 48];

        System.arraycopy(decodedData, 0, iv, 0, 16);
        System.arraycopy(decodedData, 16, hmac, 0, 32);
        System.arraycopy(decodedData, 48, ciphertext, 0, decodedData.length - 48);

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decryptedData = cipher.doFinal(ciphertext);

        String hmacCalculated = new String(hmac(decryptedData, key.getBytes(StandardCharsets.UTF_8)));
        String hmacReceived = new String(hmac);

        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    private static byte[] hmac(final byte[] input, final byte[] key) {
        try {
            final Mac hmac = Mac.getInstance("HmacSHA256");
            hmac.init(new SecretKeySpec(key, "HmacSHA256"));
            return hmac.doFinal(input);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }
    }

}

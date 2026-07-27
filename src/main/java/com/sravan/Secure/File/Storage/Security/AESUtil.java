package com.sravan.Secure.File.Storage.Security;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Component
public class AESUtil {

    private static final String ALGORITHM = "AES";

    // Generate a random AES-256 key
    public SecretKey generateKey() throws Exception {

        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256);

        return keyGenerator.generateKey();
    }

    // Encrypt file bytes
    public byte[] encrypt(byte[] data, SecretKey key) throws Exception {

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    // Decrypt file bytes
    public byte[] decrypt(byte[] encryptedData, SecretKey key) throws Exception {

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(encryptedData);
    }
}
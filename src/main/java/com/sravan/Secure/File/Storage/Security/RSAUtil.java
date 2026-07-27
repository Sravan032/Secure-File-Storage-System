package com.sravan.Secure.File.Storage.Security;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

@Component
public class RSAUtil {

    private static final String PUBLIC_KEY_PATH =
            "src/main/resources/keys/public.key";

    private static final String PRIVATE_KEY_PATH =
            "src/main/resources/keys/private.key";

    private PublicKey getPublicKey() throws Exception {

        byte[] bytes = Files.readAllBytes(Paths.get(PUBLIC_KEY_PATH));

        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);

        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    private PrivateKey getPrivateKey() throws Exception {

        byte[] bytes = Files.readAllBytes(Paths.get(PRIVATE_KEY_PATH));

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);

        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    public byte[] encryptAESKey(SecretKey aesKey) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());

        return cipher.doFinal(aesKey.getEncoded());
    }

    public SecretKey decryptAESKey(byte[] encryptedKey) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());

        byte[] decryptedKey = cipher.doFinal(encryptedKey);

        return new SecretKeySpec(decryptedKey, "AES");
    }
}
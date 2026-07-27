package com.sravan.Secure.File.Storage.Security;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

@Component
public class KeyManager {

    private static final String KEY_DIRECTORY = "src/main/resources/keys";
    private static final String PUBLIC_KEY = "public.key";
    private static final String PRIVATE_KEY = "private.key";

    public void generateKeysIfAbsent() throws Exception {

        Path keyDir = Paths.get(KEY_DIRECTORY);

        if (!Files.exists(keyDir)) {
            Files.createDirectories(keyDir);
        }

        Path publicKeyPath = keyDir.resolve(PUBLIC_KEY);
        Path privateKeyPath = keyDir.resolve(PRIVATE_KEY);

        if (Files.exists(publicKeyPath) && Files.exists(privateKeyPath)) {
            return;
        }

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair keyPair = generator.generateKeyPair();

        Files.write(publicKeyPath, keyPair.getPublic().getEncoded());
        Files.write(privateKeyPath, keyPair.getPrivate().getEncoded());

        System.out.println("RSA Key Pair Generated Successfully.");
    }
}
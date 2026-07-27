package com.sravan.Secure.File.Storage.Startup;

import com.sravan.Secure.File.Storage.Security.KeyManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final KeyManager keyManager;

    public StartupRunner(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    @Override
    public void run(String... args) throws Exception {
        keyManager.generateKeysIfAbsent();
    }
}
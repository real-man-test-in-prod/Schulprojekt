package com.rtfm.hammer.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class SaveCodeGenerator {

    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // no O/0/1/I ambiguity
    private final SecureRandom random = new SecureRandom();

    public String generate() {
        // Format: XXXX-XXXX-XXXX (12 chars = ~60 bits of entropy)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            if (i > 0 && i % 4 == 0) sb.append('-');
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
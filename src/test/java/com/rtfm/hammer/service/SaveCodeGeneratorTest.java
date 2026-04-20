package com.rtfm.hammer.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SaveCodeGeneratorTest {

    private final SaveCodeGenerator generator = new SaveCodeGenerator();

    @Test
    void shouldGenerateCodeWithCorrectFormat() {
        String code = generator.generate();

        assertTrue(code.matches("[A-HJ-NP-Z2-9]{4}-[A-HJ-NP-Z2-9]{4}-[A-HJ-NP-Z2-9]{4}"),
                "Code should match format XXXX-XXXX-XXXX with allowed characters");
    }

    @Test
    void shouldGenerateCodeWithCorrectLength() {
        String code = generator.generate();

        assertEquals(14, code.length(), "Code should be 14 characters including dashes");
    }

    @Test
    void shouldGenerateUniqueCodes() {
        String code1 = generator.generate();
        String code2 = generator.generate();

        assertNotEquals(code1, code2, "Generated codes should be unique");
    }
}
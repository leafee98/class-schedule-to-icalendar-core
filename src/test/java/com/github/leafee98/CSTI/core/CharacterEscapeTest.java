package com.github.leafee98.CSTI.core;

import com.github.leafee98.CSTI.core.utils.CharacterEscape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CharacterEscapeTest {

    @Test
    void testEscape() {
        String input = "abc,\n" + "def:\n" + "ghi;\n" + "\"hello\"\n" + "\\";
        String actual = CharacterEscape.escape(input);
        String expect = "abc\\,\\ndef:\\nghi\\;\\n\"hello\"\\n\\\\";
        Assertions.assertEquals(expect, actual);
    }
}

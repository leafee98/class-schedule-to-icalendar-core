package com.github.leafee98.CSTI.core.utils;

import java.util.Arrays;
import java.util.List;

public class CharacterEscape {

    private static class CharPair {
        public CharPair(String from, String to) {
            this.from = from;
            this.to = to;
        }
        public String from;
        public String to;
    }

    private static final List<CharPair> escapeList = Arrays.asList(
            // the holy shit escape in java, regex. why isn't there a raw string?
            new CharPair("\\\\(?![;,n])", "\\\\\\\\"),
            new CharPair(";", "\\\\;"),
            new CharPair(",", "\\\\,"),
            new CharPair("\\n", "\\\\n")
            // the char ":" is not needed to escape
    );

    public static String escape(String str) {
        for (CharPair x : escapeList) {
            str = str.replaceAll(x.from, x.to);
        }
        return str;
    }
}

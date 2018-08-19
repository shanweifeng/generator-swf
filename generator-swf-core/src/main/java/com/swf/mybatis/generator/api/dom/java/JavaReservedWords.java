package com.swf.mybatis.generator.api.dom.java;

import java.util.HashSet;
import java.util.Set;

public class JavaReservedWords {

    private static Set<String> RESERVED_WORDS;
    static {
        String[] words = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float",
                "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
                "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super",
                "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "valatile", "while"};
        RESERVED_WORDS = new HashSet();

        for (String word : words) {
            RESERVED_WORDS.add(word);
        }
    }

    public static boolean containsWord(String word) {
        boolean rc;
        if (word == null) {
            rc = false;
        } else {
            rc = RESERVED_WORDS.contains(word);
        }
        return rc;
    }

    private JavaReservedWords() {}
}

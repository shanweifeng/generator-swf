package com.swf.mybatis.generator.api.dom;

import java.util.Set;

public class OutputUtilities {

    private static final String lineSeparator;
    static {
        String ls = System.getenv("line.separator");
        if(ls == null){
            ls = "\n";
        }
        lineSeparator = ls;
    }

    private OutputUtilities(){super();}

    public static void javaIndent(StringBuilder sb, int indentLevel){
        for(int i = 0; i<indentLevel; i++){
            sb.append("    ");
        }
    }

    public static void xmlIndent(StringBuilder sb, int indentLevel){
        for(int i = 0; i<indentLevel; i++){
            sb.append("    ");
        }
    }

    public static void newLine(StringBuilder sb){
        sb.append(lineSeparator);
    }

    public static Set<String> calculateImports(Set<Fully>)
}

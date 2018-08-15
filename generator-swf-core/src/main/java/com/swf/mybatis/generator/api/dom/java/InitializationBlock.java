package com.swf.mybatis.generator.api.dom.java;

import com.swf.mybatis.generator.api.dom.OutputUtilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class InitializationBlock {
    private boolean isStatic;
    private List<String> bodyLines;
    private List<String> javaDocLines;

    public InitializationBlock(){this(false);}

    public InitializationBlock(boolean isStatic){
        this.isStatic = isStatic;
        bodyLines = new ArrayList<>();
        javaDocLines = new ArrayList<>();
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public List<String> getBodyLines() {
        return bodyLines;
    }

    public void addBodyLine(String line){ bodyLines.add(line);}

    public void addBodyLine(int index,String line){
        bodyLines.add(index,line);
    }

    public void addBodyLines(Collection<String> lines){
        bodyLines.addAll(lines);
    }

    public void addBodyLines(int index,Collection<String> lines){
        bodyLines.addAll(index,lines);
    }

    public List<String> getJavaDocLines() {
        return javaDocLines;
    }

    public void addJavaDocLine(String javaDocLine){
        javaDocLines.add(javaDocLine);
    }

    public String getFormattedContent(int indentLevel){
        StringBuilder sb = new StringBuilder();

        for (String javaDocLine : javaDocLines){
            OutputUtilities.javaIndent(sb,indentLevel);
            sb.append(javaDocLine);
            OutputUtilities.newLine(sb);
        }

        OutputUtilities.javaIndent(sb,indentLevel);

        if(isStatic){
            sb.append("static ");
        }

        sb.append('{');
        indentLevel++;
        ListIterator<String> listIter = bodyLines.listIterator();
        while(listIter.hasNext()){
            String line  = listIter.next();
            if(line.startsWith("}")){
                indentLevel--;
            }

            OutputUtilities.newLine(sb);
            OutputUtilities.javaIndent(sb,indentLevel);
            sb.append(line);

            if((line.endsWith("{") && !line.startsWith("switch")) || line.endsWith(":")){
                indentLevel++;
            }

            if(line.startsWith("break")){
                if(listIter.hasNext()){
                    String nextLine = listIter.next();
                    if(nextLine.startsWith("}")){
                        indentLevel++;
                    }
                    //set back to the previous element
                    listIter.previous();
                }
                indentLevel--;
            }
        }
        indentLevel--;
        OutputUtilities.newLine(sb);
        OutputUtilities.javaIndent(sb,indentLevel);
        sb.append('}');
        return sb.toString();
    }
}

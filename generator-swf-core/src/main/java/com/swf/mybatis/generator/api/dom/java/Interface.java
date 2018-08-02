package com.swf.mybatis.generator.api.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.swf.mybatis.generator.api.dom.OutputUtilities.calculateImports;
import static com.swf.mybatis.generator.api.dom.OutputUtilities.newLine;
import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class Interface extends InnerInterface implements CompilationUnit {

    private Set<FullyQualifiedJavaType> importedTypes;

    private Set<String> staticImports;

    private List<String> fileCommentLines;

    public Interface(FullyQualifiedJavaType type){
        super(type);
        importedTypes = new TreeSet<>();
        fileCommentLines = new ArrayList<>();
        staticImports = new TreeSet<>();
    }

    public Interface(String type){
        this(new FullyQualifiedJavaType(type));
    }

    @Override
    public Set<FullyQualifiedJavaType> getImportedTypes() {
        return importedTypes;
    }

    @Override
    public void addImportType(FullyQualifiedJavaType importedType) {
        if(importedType.isExplicitlyImported() && !importedType.getPackageName().equals(getType().getPackageName())){
            importedTypes.add(importedType);
        }
    }

    @Override
    public String getFormattedContent() {
        return getFormattedContent(0,this);
    }

    @Override
    public String getFormattedContent(int indentLevel,CompilationUnit compilationUnit) {
        StringBuilder sb = new StringBuilder();

        for(String commentLine : fileCommentLines){
            sb.append(commentLine);
            newLine(sb);
        }

        if(stringHasValue(getType().getPackageName())){
            sb.append("package ");
            sb.append(getType().getPackageName());
            sb.append(';');
            newLine(sb);
            newLine(sb);
        }

        for(String staticImport : staticImports){
            sb.append("import static ");
            sb.append(staticImport);
            sb.append(';');
            newLine(sb);
        }

        if(staticImports.size() > 0){
            newLine(sb);
        }

        Set<String> importStrings = calculateImports(importedTypes);
        for(String importString : importStrings){
            sb.append(importString);
            newLine(sb);
        }

        if(importStrings.size() > 0){
            newLine(sb);
        }

        sb.append(super.getFormattedContent(0,this));
        return sb.toString();
    }

    @Override
    public void addFileCommentLine(String commentLine) {
        fileCommentLines.add(commentLine);
    }

    @Override
    public List<String> getFileCommentLines() {
        return fileCommentLines;
    }

    @Override
    public void addImportTypes(Set<FullyQualifiedJavaType> importedTypes) {
        this.importedTypes.addAll(importedTypes);
    }

    @Override
    public Set<String> getStaticImport() {
        return staticImports;
    }

    @Override
    public void addStaticImport(String staticImport) {
        staticImports.add(staticImport);
    }

    @Override
    public void addStaticImports(Set<String> staticImports) {
        this.staticImports.addAll(staticImports);
    }
}

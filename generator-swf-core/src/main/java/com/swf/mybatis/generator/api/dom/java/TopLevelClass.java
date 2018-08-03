package com.swf.mybatis.generator.api.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.swf.mybatis.generator.api.dom.OutputUtilities.calculateImports;
import static com.swf.mybatis.generator.api.dom.OutputUtilities.newLine;
import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class TopLevelClass extends InnerClass implements CompilationUnit {

    private Set<FullyQualifiedJavaType> importedTypes;

    private Set<String> staticImports;

    private List<String> fileCOmmentLines;

    public TopLevelClass(FullyQualifiedJavaType type){
        super(type);
        importedTypes = new TreeSet<>();
        fileCOmmentLines = new ArrayList<>();
        staticImports = new TreeSet<>();
    }

    public TopLevelClass(String typeName){
        this(new FullyQualifiedJavaType(typeName));
    }

    @Override
    public Set<FullyQualifiedJavaType> getImportedTypes() {
        return importedTypes;
    }

    public void addImportedType(String importedType){
        addImportedType(importedType);
    }

    @Override
    public void addImportedType(FullyQualifiedJavaType importedType){
        if(importedType != null && importedType.isExplicitlyImported()
                && !importedType.getPackageName().equals(getType().getPackageName())
                && !importedType.getShortName().equals(getType().getShortName())){
            importedTypes.add(importedType);
        }
    }

    @Override
    public void addImportedTypes(Set<FullyQualifiedJavaType> importedTypes) {
        this.importedTypes.addAll(importedTypes);
    }

    @Override
    public String getFormattedContent() {
        StringBuilder sb = new StringBuilder();

        for (String fileCommentLine : fileCOmmentLines){
            sb.append(fileCommentLine);
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
        for (String importString : importStrings){
            sb.append(importString);
            newLine(sb);
        }

        sb.append(super.getFormattedContent(0,this));
        return sb.toString();
    }

    @Override
    public boolean isJavaInterface() {
        return false;
    }

    @Override
    public boolean isJavaEnumeration() {
        return false;
    }

    @Override
    public void addFileCommentLine(String commentLine) {
        fileCOmmentLines.add(commentLine);
    }

    @Override
    public List<String> getFileCommentLines() {
        return fileCOmmentLines;
    }

    @Override
    public Set<String> getStaticImport() {
        return staticImports;
    }

    @Override
    public void addStaticImport(String staticImport) {
        this.staticImports.add(staticImport);
    }

    @Override
    public void addStaticImports(Set<String> staticImports) {
        this.staticImports.addAll(staticImports);
    }
}

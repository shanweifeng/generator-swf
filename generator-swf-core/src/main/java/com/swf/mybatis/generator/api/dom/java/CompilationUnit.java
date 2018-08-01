package com.swf.mybatis.generator.api.dom.java;

import java.util.List;
import java.util.Set;

public interface CompilationUnit {
    String getFormattedContent();

    Set<FullyQualifiedJavaType> getImportedTypes();

    Set<String> getStaticImport();

    FullyQualifiedJavaType getSuperClass();

    boolean isJavaInterface();

    boolean isJavaEnumeration();

    Set<FullyQualifiedJavaType> getSuperInterfaceTypes();

    FullyQualifiedJavaType getType();

    void addImportType(FullyQualifiedJavaType importedType);

    void addImportTypes(Set<FullyQualifiedJavaType> importedTypes);

    void addStaticImport(String staticImport);

    void addStaticImports(Set<String> staticimports);

    void addFileCommentLine(String commentLine);

    List<String> getFileCommentLines();
}

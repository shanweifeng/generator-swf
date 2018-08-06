package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.api.dom.java.Field;
import com.swf.mybatis.generator.exception.ShellException;

import java.io.File;

public interface ShellCallback {

    File getDirectory(String targetProject, String targetPackage) throws ShellException;

    String mergeJavaFile(String newFileSource, File existingFile, String[] javadocTags, String fileEncoding) throws ShellException;

    void refreshProject(String project);

    boolean isMergeSupported();

    boolean isOverwriteEnabled();
}

package com.swf.mybatis.generator.internal;

import com.swf.mybatis.generator.api.ShellCallback;
import com.swf.mybatis.generator.api.dom.java.Field;
import com.swf.mybatis.generator.exception.ShellException;

import java.io.File;
import java.util.StringTokenizer;

import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class DefaultShellCallback implements ShellCallback {

    private boolean overwrite;

    public DefaultShellCallback(boolean overwrite) {
        super();
        this.overwrite = overwrite;
    }

    @Override
    public File getDirectory(String targetProject, String targetPackage) throws ShellException {
        File project = new File(targetProject);
        if (!project.isDirectory()) {
            throw new ShellException(getString("Warning.9", targetProject));
        }

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(targetPackage,".");
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            sb.append(File.separatorChar);
        }

        File directory = new File(project, sb.toString());
        if (!directory.isDirectory()){
            boolean rc = directory.mkdirs();
            if (!rc) {
                throw new ShellException(getString("Warning.10",directory.getAbsolutePath()));
            }
        }
        return directory;
    }

    @Override
    public String mergeJavaFile(String newFileSource, File existingFile, String[] javadocTags, String fileEncoding) throws ShellException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refreshProject(String project) {
        // nothing to do in the default shell callback
    }

    @Override
    public boolean isMergeSupported() {
        return false;
    }

    @Override
    public boolean isOverwriteEnabled() {
        return overwrite;
    }
}

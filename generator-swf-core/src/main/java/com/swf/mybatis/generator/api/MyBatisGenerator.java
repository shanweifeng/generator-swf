package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.codegen.RootClassInfo;
import com.swf.mybatis.generator.config.Configuration;
import com.swf.mybatis.generator.config.Context;
import com.swf.mybatis.generator.config.MergeConstants;
import com.swf.mybatis.generator.exception.InvalidConfigurationException;
import com.swf.mybatis.generator.exception.ShellException;
import com.swf.mybatis.generator.internal.DefaultShellCallback;
import com.swf.mybatis.generator.internal.NullProgressCallback;
import com.swf.mybatis.generator.internal.ObjectFactory;
import com.swf.mybatis.generator.internal.XmlFileMergerJaxp;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.swf.mybatis.generator.internal.util.ClassloaderUtility.getCustomClassloader;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class MyBatisGenerator {

    private Configuration configuration;

    private ShellCallback shellCallback;

    private List<GeneratedJavaFile> generatedJavaFiles;

    private List<GeneratedXmlFile> generatedXmlFiles;

    private List<String> warnings;

    private Set<String> projects;

    public MyBatisGenerator(Configuration configuration, ShellCallback shellCallback, List<String> warnings)
    throws InvalidConfigurationException {
        super();
        if (configuration == null) {
            throw new IllegalArgumentException(getString("RunTimeError.2"));
        } else {
            this.configuration = configuration;
        }

        if (shellCallback == null) {
            this.shellCallback = new DefaultShellCallback(false);
        } else {
            this.shellCallback = shellCallback;
        }

        if (warnings == null) {
            this.warnings = new ArrayList<>();
        } else {
            this.warnings = warnings;
        }

        generatedJavaFiles = new ArrayList<>();
        generatedXmlFiles = new ArrayList<>();
        projects = new HashSet<>();
        this.configuration.validate();
    }

    public void generate(ProgressCallback callback) throws SQLException, IOException, InterruptedException {
        generate(callback, null, null, true);
    }

    public void generate(ProgressCallback callback, Set<String> contextIds) throws SQLException, IOException, InterruptedException{
        generate(callback, contextIds, null, true);
    }

    public void generate(ProgressCallback callback, Set<String> contextIds, Set<String> fullyQualifiedTableNames) throws SQLException,
            IOException, InterruptedException {
        generate(callback, contextIds, fullyQualifiedTableNames, true);
    }

    public void generate(ProgressCallback callback, Set<String> contextIds, Set<String> fullyQualifiedTableNames, boolean writeFils) throws SQLException,
            IOException, InterruptedException {
        if (callback == null) {
            callback = new NullProgressCallback();
        }

        generatedJavaFiles.clear();
        generatedXmlFiles.clear();
        ObjectFactory.reset();
        RootClassInfo.reset();

        List<Context> contextsToRun;
        if (contextIds == null || contextIds.size() == 0) {
            contextsToRun = configuration.getContexts();
        } else {
            contextsToRun =  new ArrayList<>();
            for (Context context : configuration.getContexts()) {
                if (contextIds.contains(context.getId())) {
                    contextsToRun.add(context);
                }
            }
        }

        if (configuration.getClassPathEntries().size() > 0) {
            ClassLoader classLoader = getCustomClassloader(configuration.getClassPathEntries());
            ObjectFactory.addExternalClassLoader(classLoader);
        }

        int totalSteps = 0;
        for (Context context : contextsToRun) {
            totalSteps += context.getIntrospectionSteps();
        }
        callback.introspectionStarted(totalSteps);

        for (Context context : contextsToRun) {
            context.introspectTables(callback, warnings, fullyQualifiedTableNames);
        }

        totalSteps = 0;
        for (Context context : contextsToRun) {
            totalSteps += context.getGenerationSteps();
        }
        callback.generationStarted((totalSteps));

        for (Context context : contextsToRun) {
            context.generateFiles(callback, generatedJavaFiles, generatedXmlFiles,warnings);
        }

        if (writeFils) {
            callback.saveStarted(generatedXmlFiles.size() + generatedJavaFiles.size());

            for (GeneratedXmlFile gxf : generatedXmlFiles) {
                projects.add(gxf.getTargetProject());
                writeGeneratedXmlFile(gxf, callback);
            }

            for (GeneratedJavaFile gjf : generatedJavaFiles) {
                projects.add(gjf.getTargetProject());
                writeGeneratedJavaFile(gjf, callback);
            }

            for (String project : projects) {
                shellCallback.refreshProject(project);
            }
        }

        callback.done();
    }

    private void writeGeneratedJavaFile(GeneratedJavaFile gjf, ProgressCallback callback)
        throws InterruptedException, IOException {
        File targetFile;
        String source;
        try {
            File directory = shellCallback.getDirectory(gjf.getTargetProject(), gjf.getTargetPackage());
            targetFile = new File(directory, gjf.getFileName());
            if (targetFile.exists()) {
                if (shellCallback.isMergeSupported()) {
                    source = shellCallback.mergeJavaFile(gjf.getFormattedContent(), targetFile, MergeConstants.OLD_ELEMENT_TAGS, gjf.getFileEncoding());
                } else if (shellCallback.isOverwriteEnabled()) {
                    source = gjf.getFormattedContent();
                    warnings.add(getString("Warning.11", targetFile.getAbsolutePath()));
                } else {
                    source = gjf.getFormattedContent();
                    targetFile = getUniqueFileName(directory, gjf.getFileName());
                    warnings.add(getString("Warning.2", targetFile.getAbsolutePath()));
                }
            } else {
                source = gjf.getFormattedContent();
            }

            callback.checkCancel();
            callback.startTask((getString("Progress.15", targetFile.getName())));
            writeFile(targetFile, source, gjf.getFileEncoding());
        } catch (ShellException e) {
            warnings.add(e.getMessage());
        }
    }

    private void writeGeneratedXmlFile(GeneratedXmlFile gxf, ProgressCallback callback) throws InterruptedException, IOException {
        File targetFile;
        String source;
        try {
            File directory = shellCallback.getDirectory(gxf.getTargetProject(), gxf.getTargetPackage());
            targetFile = new File(directory, gxf.getFileName());
            if (targetFile.exists()) {
                if (gxf.isMMergeable()) {
                    source = XmlFileMergerJaxp.getMergedSource(gxf, targetFile);
                } else if (shellCallback.isOverwriteEnabled()) {
                    source = gxf.getFormattedContent();
                    warnings.add(getString("Warning.11", targetFile.getAbsolutePath()));
                } else {
                    source = gxf.getFormattedContent();
                    targetFile = getUniqueFileName(directory, gxf.getFileName());
                    warnings.add(getString("Warning.2", targetFile.getAbsolutePath()));
                }
            } else {
                source = gxf.getFormattedContent();
            }
            callback.checkCancel();
            callback.startTask(getString("Progress.15", targetFile.getName()));
        } catch (ShellException e) {
            warnings.add(e.getMessage());
        }
    }

    private void writeFile(File file, String content, String fileEncoding) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, false);
        OutputStreamWriter osw;
        if (fileEncoding == null) {
            osw = new OutputStreamWriter(fos);
        } else {
            osw = new OutputStreamWriter(fos,fileEncoding);
        }
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(content);
        bw.close();
    }

    private File getUniqueFileName(File directory, String fileName) {
        File answer = null;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 1000; i++) {
            sb.setLength(0);
            sb.append(fileName);
            sb.append('.');
            sb.append(i);
            File testFile = new File(directory, sb.toString());
            if (!testFile.exists()) {
                answer = testFile;
                break;
            }
        }

        if (answer == null) {
            throw new RuntimeException(getString("RuntimeError.3", directory.getAbsolutePath()));
        }

        return answer;
    }

    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        return generatedJavaFiles;
    }

    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        return generatedXmlFiles;
    }
}

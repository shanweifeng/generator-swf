package com.swf.mybatis.generator.api;

public interface ProgressCallback {

    void introspectionStarted(int totalTasks);

    void generationStarted(int totalTasks);

    void saveStarted(int totalTasks);

    void startTask(String taskName);

    void done();

    void checkCancel() throws InterruptedException;
}

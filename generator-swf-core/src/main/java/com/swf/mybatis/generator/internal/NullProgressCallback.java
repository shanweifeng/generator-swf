package com.swf.mybatis.generator.internal;

import com.swf.mybatis.generator.api.ProgressCallback;

public class NullProgressCallback implements ProgressCallback {

    public
    NullProgressCallback() {
        super();
    }
    @Override
    public void introspectionStarted(int totalTasks) {

    }

    @Override
    public void generationStarted(int totalTasks) {

    }

    @Override
    public void saveStarted(int totalTasks) {

    }

    @Override
    public void startTask(String taskName) {

    }

    @Override
    public void done() {

    }

    @Override
    public void checkCancel() throws InterruptedException {

    }
}

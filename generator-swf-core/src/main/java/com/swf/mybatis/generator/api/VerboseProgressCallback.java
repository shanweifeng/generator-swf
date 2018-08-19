package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.internal.NullProgressCallback;

public class VerboseProgressCallback extends NullProgressCallback {

    public VerboseProgressCallback() {
        super();
    }

    @Override
    public void startTask(String taskName) {
        System.out.println(taskName);
    }
}

package com.swf.mybatis.generator.ant;

import com.swf.mybatis.generator.internal.NullProgressCallback;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

public class AntProgressCallback extends NullProgressCallback {
    private Task task;
    private boolean verbose;

    public AntProgressCallback(Task task, boolean verbose) {
        super();
        this.task = task;
        this.verbose = verbose;
    }

    @Override
    public void startTask(String subTaskName) {
        if (verbose) {
            task.log(subTaskName, Project.MSG_VERBOSE);
            task.log(subTaskName, Project.MSG_VERBOSE);
        }
    }
}

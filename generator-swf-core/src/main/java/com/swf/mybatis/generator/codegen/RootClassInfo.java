package com.swf.mybatis.generator.codegen;

import com.swf.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootClassInfo {

    private static Map<String,RootClassInfo> rootClassInfoMap;

    static {
        rootClassInfoMap = Collections.synchronizedMap(new HashMap<String,RootClassInfo>());
    }

    public static RootClassInfo getInstance(String className,List<String> warning){
        RootClassInfo classInfo = rootClassInfoMap.get(className);
        if(classInfo == null){
            classInfo = new RootClassInfo(classInfo,warning);
            rootClassInfoMap.put(className,classInfo);
        }
        return classInfo;
    }

    public static void reset(){
        rootClassInfoMap.clear();
    }

    public PropertyDescriptor[] propertyDescriptors;

    private String className;

    private List<String> warning;

    private boolean genericMode = false;

    private RootClassInfo(String className,List<String> warnings){
        super();
        this.className = className;
        this.warning = warnings;

        if(className == null){
            return;
        }

        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(className);
        String nameWithoutGenerics = fqjt.getFullyQualifiedNameWithoutTypeParameters();
        if(!nameWithoutGenerics.equals(className)){
            genericMode = true;
        }

        try{
            Class<?> clazz = ObjectFactory.ex
        }
    }
}

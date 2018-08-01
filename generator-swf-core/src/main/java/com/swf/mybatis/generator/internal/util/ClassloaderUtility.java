package com.swf.mybatis.generator.internal.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class ClassloaderUtility {
    private ClassloaderUtility(){}

    public static ClassLoader getCustomClassloader(Collection<String> entries){

        List<URL> urls = new ArrayList<>();
        File file;

        if(entries != null){
            for(String classPathEntry : entries){
                file = new File(classPathEntry);
                if(!file.exists()){
                    throw new RuntimeException(getString("RuntimeError.9",classPathEntry));
                }

                try{
                    urls.add(file.toURI().toURL());
                }catch (MalformedURLException e){
                    throw new RuntimeException(getString("RuntieError.9",classPathEntry));
                }
            }
        }

        ClassLoader parent = Thread.currentThread().getContextClassLoader();

        URLClassLoader ucl = new URLClassLoader(urls.toArray(new URL[urls.size()]),parent);

        return ucl;
    }
}

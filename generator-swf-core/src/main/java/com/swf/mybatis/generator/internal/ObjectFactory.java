package com.swf.mybatis.generator.internal;

import com.swf.mybatis.generator.api.CommentGenerator;
import com.swf.mybatis.generator.api.JavaTypeResolver;
import com.swf.mybatis.generator.config.Context;
import com.swf.mybatis.generator.config.JavaTypeResolverConfiguration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class ObjectFactory {

    private static List<ClassLoader> externalClassLoaders;

    static {
        externalClassLoaders = new ArrayList<>();
    }

    private ObjectFactory(){super();}

    public static void reset(){externalClassLoaders.clear();}

    public static synchronized void addExternalClassLoader(ClassLoader classLoader){
        ObjectFactory.externalClassLoaders.add(classLoader);
    }

    public static Class<?> externalClassForName(String type) throws ClassNotFoundException{
        Class<?> clazz;
        for(ClassLoader classLoader : externalClassLoaders){
            try{
                clazz = Class.forName(type,true,classLoader);
                return clazz;
            }catch (Throwable e){
                // ignore - fail safe below
            }
        }
        return internalClassForName(type);
    }

    public static Object createExternaObject(String type){
        Object answer;
        try{
            Class<?> clazz = externalClassForName(type);
            answer = clazz.newInstance();
        }catch (Exception e){
            throw new RuntimeException(getString("RuntimeError.6",type),e);
        }
        return answer;
    }

    public static Class<?> internalClassForName(String type)throws ClassNotFoundException{
        Class<?> clazz = null;
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            clazz = Class.forName(type,false,cl);
        }catch (Exception e){
            // ignore - fail safe  below
        }
        if(clazz == null){
            clazz = Class.forName(type,true,ObjectFactory.class.getClassLoader());
        }

        return clazz;
    }

    public static URL getResource(String resource){
        URL url;
        for(ClassLoader classLoader : externalClassLoaders){
            url = classLoader.getResource(resource);
            if(url != null){
                return url;
            }
        }

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        url = cl.getResource(resource);
        if(url == null){
            url = ObjectFactory.class.getClassLoader().getResource(resource);
        }
        return url;
    }

    public static Object createInternalObject(String type){
        Object answer;
        try {
            Class<?> clazz = internalClassForName(type);
            answer = clazz.newInstance();
        }catch (Exception e){
            throw new RuntimeException(getString("RuntimeError.6",type),e);
        }
        return answer;
    }

    public static JavaTypeResolver createJavaTypeResolver(Context context, List<String> warning){
        JavaTypeResolverConfiguration config = context.getJavaTypeResolverConfiguration();
        String type;
        if(config != null && config.getConfigurationType() != null){
            type = config.getConfigurationType();
            if("DEFAULT".equalsIgnoreCase(type)){
                type = JavaTypeResolverDefaultImpl.class.getName();
            }
        }else{
            type = JavaTypeResolverDefaultImpl.class.getName();
        }
        JavaTypeResolver answer =(JavaTypeResolver)createInternalObject(type);
        answer.setWarnings(warning);
        if(config != null){
            answer.addConfigurationProperties(config.getProperties());
        }
        answer.setContext(context);
        return answer;
    }

    public static Plugin createPlugin(Context context,PluginConfiguration pluginConfiguration){
        Plugin plugin = (Plugin) createInternalObject(pluginConfiguration.getConfigurationType());
        plugin.setContext(context);
        plugin.setProperties(pluginConfiguration.getProperties());
        return plugin;
    }

    public static CommentGenerator createCommentGenerator(Context context){

    }
}

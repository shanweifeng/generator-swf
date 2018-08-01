package com.swf.mybatis.generator.logging.slf4j;

import com.swf.mybatis.generator.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

public class Slf4jImpl implements Log {
    private Log log;

    public Slf4jImpl(Class<?> clazz){
        Logger logger = LoggerFactory.getLogger(clazz);

        if(logger instanceof LocationAwareLogger){
            try{
                logger.getClass().getMethod("log",Marker.class,String.class,int.class,String.class,Object[].class,Throwable.class);
                log = new Slf4jLocationAwareLoggerImpl((LocationAwareLogger) logger);
            }catch (SecurityException e){

            }catch (NoSuchMethodException e){

            }
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable e) {

    }

    @Override
    public void error(String s) {

    }

    @Override
    public void debug(String s) {

    }

    @Override
    public void warn(String s) {

    }
}

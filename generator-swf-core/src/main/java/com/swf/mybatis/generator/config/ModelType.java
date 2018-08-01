package com.swf.mybatis.generator.config;

import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public enum ModelType {
    HIERARCHICAL("hierarchical"), //$NON-NLS-1$
    FLAT("flat"), //$NON-NLS-1$
    CONDITIONAL("conditional"); //$NON-NLS-1$

    private final String modelType;

    private ModelType(String modelType){
        this.modelType = modelType;
    }

    public String getModelType() {
        return modelType;
    }

    public static ModelType getModelType(String type){
        if(HIERARCHICAL.getModelType().equalsIgnoreCase(type)){
            return HIERARCHICAL;
        }else if(FLAT.getModelType().equalsIgnoreCase(type)){
            return FLAT;
        }else if(CONDITIONAL.getModelType().equalsIgnoreCase(type)){
            return CONDITIONAL;
        }else{
            throw new RuntimeException(getString("RuntimeError.13",type));
        }
    }
}

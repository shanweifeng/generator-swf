package com.swf.mybatis.generator.api.dom.java;

import java.util.ArrayList;
import java.util.List;

public class TypeParameter {
    private String name;

    private List<FullyQualifiedJavaType> extendsTypes;

    public TypeParameter(String name){
        this(name,new ArrayList<FullyQualifiedJavaType>());
    }

    public TypeParameter(String name,List<FullyQualifiedJavaType> extendsTypes){
        super();
        this.name = name;
        this.extendsTypes = extendsTypes;
    }

    public String getName(){return name;}

    public List<FullyQualifiedJavaType> getExtendsTypes(){
        return  extendsTypes;
    }

    public String getFormattedContent(CompilationUnit compilationUnit){
        StringBuilder sb = new StringBuilder();
        sb.append(name);

        if(!extendsTypes.isEmpty()){
            sb.append(" extends ");
            boolean addAnd = false;
            for(FullyQualifiedJavaType type : extendsTypes){
                if(addAnd){
                    sb.append(" & ");
                }else{
                    addAnd = true;
                }
                sb.append(JavaDomUtils.calculateTypeName(compilationUnit,type));
            }
        }
        return sb.toString();
    }

    @Override
    public String toString(){return getFormattedContent(null);}
}

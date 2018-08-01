package com.swf.mybatis.generator.api.dom.xml;

public class Attribute implements Comparable<Attribute> {

    private String name;

    private String value;

    public Attribute(String name,String value){
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getFormattedContent(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("=\"");
        sb.append(value);
        sb.append('\"');
        return sb.toString();
    }

    @Override
    public int compareTo(Attribute o) {
        if(this.name == null){
            return o.name == null ? 0 : -1;
        }else{
            if(o.name == null){
                return 0;
            }else{
                return this.name.compareTo(o.name);
            }
        }
    }
}

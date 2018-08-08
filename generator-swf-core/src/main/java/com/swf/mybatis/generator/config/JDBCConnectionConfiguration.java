package com.swf.mybatis.generator.config;

import com.swf.mybatis.generator.api.dom.xml.Attribute;
import com.swf.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class JDBCConnectionConfiguration extends PropertyHolder {

    private String driverClass;

    private String connectionURL;

    private String userId;

    private String password;

    public JDBCConnectionConfiguration(){super();}

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public XmlElement toXmlElement(){
        XmlElement xmlElement = new XmlElement("jdbcConnection");
        xmlElement.addAttribute(new Attribute("driverClass",driverClass));
        xmlElement.addAttribute(new Attribute("connectionURL",connectionURL));

        if(stringHasValue(userId)){
            xmlElement.addAttribute(new Attribute("userId",userId));
        }

        if(stringHasValue(password)){
            xmlElement.addAttribute(new Attribute("password",password));
        }

        addPropertyXmlElements(xmlElement);
        return xmlElement;
    }

    public void validate(List<String> errors){
        if(!stringHasValue(driverClass)){
            errors.add(getString("ValidationError.4"));
        }

        if(!stringHasValue(connectionURL)){
            errors.add(getString("ValidationError.5"));
        }
    }
}

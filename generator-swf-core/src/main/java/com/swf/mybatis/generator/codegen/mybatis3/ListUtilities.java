package com.swf.mybatis.generator.codegen.mybatis3;

import com.swf.mybatis.generator.api.IntrospectedColumn;

import java.util.ArrayList;
import java.util.List;

public class ListUtilities {

    public static List<IntrospectedColumn> removeGeneratedAlwaysColumns(List<IntrospectedColumn> columns) {
        List<IntrospectedColumn> filteredList = new ArrayList<>();
        for (IntrospectedColumn ic : columns) {
            if (!ic.isGeneratedAlways()) {
                filteredList.add(ic);
            }
        }

        return filteredList;
    }

    public static List<IntrospectedColumn> removeIdentityAndGeneratedAlwaysColumns(List<IntrospectedColumn> columns) {
        List<IntrospectedColumn> filteredList = new ArrayList<>();
        for (IntrospectedColumn ic : columns) {
            if (!ic.isGeneratedAlways() && !ic.isIdentity()) {
                filteredList.add(ic);
            }
        }

        return filteredList;
    }
}

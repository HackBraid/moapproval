package com.longfor.bean;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by issuser on 2017/11/2.
 */
public class FlowParamBean extends ParamBean{

    private String systemNo;
    private String flowNo;
    private String usercode;


    public String getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }
}

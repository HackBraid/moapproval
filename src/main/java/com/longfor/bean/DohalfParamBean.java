package com.longfor.bean;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by issuser on 2017/11/2.
 */
public class DohalfParamBean extends ParamBean{

    @NotEmpty(message="当前用户名不能为空")//字符串不为''
    private String username;

    private String searchType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}

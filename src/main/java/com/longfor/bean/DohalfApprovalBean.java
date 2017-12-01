package com.longfor.bean;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by issuser on 2017/11/2.
 */
public class DohalfApprovalBean extends ParamBean{

    @NotEmpty(message="todoId不能为空")//字符串不为''
    private String todoId;
    private String systemNo;
    private String flowNo;
    @NotNull(message="状态设置不能")//字符串不为''
    private Integer status;

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

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

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }
}

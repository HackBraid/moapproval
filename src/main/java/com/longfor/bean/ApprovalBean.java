package com.longfor.bean;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by issuser on 2017/11/2.
 */
public class ApprovalBean{

    @NotEmpty(message="todoId不能为空")//字符串不为''
    private String todoId;
    @NotEmpty(message="data数据不能为空")//字符串不为''
//    @Expose
    private String data;
    private String systemNo;
    private String flowNo;
//    @Expose
    private Integer status;

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    //
//    @Override
//    public Integer getStatus() {
//        return status;
//    }
//
//    @Override
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
}

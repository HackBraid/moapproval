package com.longfor.bean;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by issuser on 2017/11/2.
 */
public class ApprovalBean extends ParamBean{

    @NotEmpty(message="todoId不能为空")//字符串不为''
    private String todoId;
    @NotEmpty(message="data数据不能为空")//字符串不为''
    private String data;

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
}

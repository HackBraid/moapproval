package com.longfor.bean;

import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

public class SysWorkitemcenterBean extends ParamBean{

    /** 任务显示名称 */
    private String workitemname;

    /** 工单编号 */
    private String orderid;

    /** 发起人用户ID */
    private String originator;

    /** 发起人显示名称 */
    private String originatorname;

    /** 待办人登录名 */
    private String participantcode;

    /** 任务状态，0：等待中；1：处理中；2：已完成；3：已取消 */
    private Integer status;

    /** 任务创建时间 */
    private Timestamp createdtime;

    public String getWorkitemname() {
        return workitemname;
    }

    public void setWorkitemname(String workitemname) {
        this.workitemname = workitemname;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getOriginatorname() {
        return originatorname;
    }

    public void setOriginatorname(String originatorname) {
        this.originatorname = originatorname;
    }

    public String getParticipantcode() {
        return participantcode;
    }

    public void setParticipantcode(String participantcode) {
        this.participantcode = participantcode;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Timestamp createdtime) {
        this.createdtime = createdtime;
    }
}

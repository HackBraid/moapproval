package com.longfor.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "SYS_WORKITEMCENTER")
public class SysWorkitemcenterEntity {

    /** 主键 */
    private String objectid;

    /** BPM任务ID */
    private String workitemid;

    /** 任务显示名称 */
    private String workitemname;

    /** 任务打开地址 */
    private String url;

    /** 发起人用户ID */
    private String originator;

    /** 发起人显示名称 */
    private String originatorname;

    /** 发起人登录名 */
    private String originatorcode;

    /** 待办人用户ID */
    private String participant;

    /** 待办人名称 */
    private String participantname;

    /** 待办人登录名 */
    private String participantcode;

    /** 代理人用户ID */
    private String agent;

    /** 代理人姓名 */
    private String agentname;

    /** 代理人登录名 */
    private String agentcode;

    /** 任务来源：BPM，Mis，Boss */
    private String fromSys;

    /** 任务状态，0：等待中；1：处理中；2：已完成；3：已取消 */
    private Integer status;

    /** 任务创建时间 */
    private Timestamp createdtime;

    /** 任务完成时间 */
    private Timestamp finishedtime;

    /** 工单编号 */
    private String OrderID;

    @Id
    @Column(nullable = false)
    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getWorkitemid() {
        return workitemid;
    }

    public void setWorkitemid(String workitemid) {
        this.workitemid = workitemid;
    }

    public String getWorkitemname() {
        return workitemname;
    }

    public void setWorkitemname(String workitemname) {
        this.workitemname = workitemname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getOriginatorcode() {
        return originatorcode;
    }

    public void setOriginatorcode(String originatorcode) {
        this.originatorcode = originatorcode;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getParticipantname() {
        return participantname;
    }

    public void setParticipantname(String participantname) {
        this.participantname = participantname;
    }

    public String getParticipantcode() {
        return participantcode;
    }

    public void setParticipantcode(String participantcode) {
        this.participantcode = participantcode;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(String agentcode) {
        this.agentcode = agentcode;
    }

    public String getFromSys() {
        return fromSys;
    }

    public void setFromSys(String fromSys) {
        this.fromSys = fromSys;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Timestamp createdtime) {
        this.createdtime = createdtime;
    }

    public Timestamp getFinishedtime() {
        return finishedtime;
    }

    public void setFinishedtime(Timestamp finishedtime) {
        this.finishedtime = finishedtime;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }
}

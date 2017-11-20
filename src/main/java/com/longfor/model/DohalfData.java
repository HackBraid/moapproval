package com.longfor.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 待办信息。
 */
@Entity
@Table(name = "DOHALFDATA")
public class DohalfData{

	/** id */
	private Integer id;

	/** 待办id/已办id */
	private String todoId;

	/** 系统编号 */
	private String systemNo;

	/** 标题 */
	private String title;

	/**DOHALFDATA 状态 0:待审批，1:已审批，2:已取消 3:已删除 4:审批中 5:审批失败 */
	private Integer todoStatus;

	/** 流程编号 */
	private String flowNo;

	/** 业务类型 */
	private String business_type;

	/** 发布人用户名 */
	private String pubUsername;

	/** 审批人用户名 */
	private String appvUsername;

	/** 待办数据 */
	private String dohalfData;

	/** 创建时间 */
	private Date createDate;

	/** 更新时间 */
	private Date updateDate;

	/** 步骤id */
	private String runnote_id;

	/** oa_url(bmp需传) */
	private String oa_url;

	/** 发布人真实姓名 */
	private String pubTrueName;

	/** 待办类型 0待办，1通知，2传阅 3、催办、4归档 */
	private Integer todo_type;

	/** 催办 0为催办 */
	private String otherStatus;

	/** 未开放数据 0为未开放 */
	private Integer notOpen;

	/** 是否是滑动删除 0为删除 */
	private Integer slideDel;
	
	/** 点击次数 */
	private Integer opentimer;
	
	/** 逻辑删除 0为删除 */
	private Integer logicDel;

	// 主键 ：@Id 主键生成方式：strategy = "increment"
	// 映射表中id这个字段，不能为空，并且是唯一的
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getPubUsername() {
		return pubUsername;
	}

	public void setPubUsername(String pubUsername) {
		this.pubUsername = pubUsername;
	}

	public String getAppvUsername() {
		return appvUsername;
	}

	public void setAppvUsername(String appvUsername) {
		this.appvUsername = appvUsername;
	}

	public String getDohalfData() {
		return dohalfData;
	}

	public void setDohalfData(String dohalfData) {
		this.dohalfData = dohalfData;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}

	public Integer getTodoStatus() {
		return todoStatus;
	}

	public void setTodoStatus(Integer todoStatus) {
		this.todoStatus = todoStatus;
	}

	public String getRunnote_id() {
		return runnote_id;
	}

	public void setRunnote_id(String runnote_id) {
		this.runnote_id = runnote_id;
	}

	public String getOa_url() {
		return oa_url;
	}

	public void setOa_url(String oa_url) {
		this.oa_url = oa_url;
	}

	public String getPubTrueName() {
		return pubTrueName;
	}

	public void setPubTrueName(String pubTrueName) {
		this.pubTrueName = pubTrueName;
	}

	public Integer getTodo_type() {
		return todo_type;
	}

	public void setTodo_type(Integer todo_type) {
		this.todo_type = todo_type;
	}

	public String getOtherStatus() {
		return otherStatus;
	}

	public void setOtherStatus(String otherStatus) {
		this.otherStatus = otherStatus;
	}

	public Integer getNotOpen() {
		return notOpen;
	}

	public void setNotOpen(Integer notOpen) {
		this.notOpen = notOpen;
	}

	public Integer getSlideDel() {
		return slideDel;
	}

	public void setSlideDel(Integer slideDel) {
		this.slideDel = slideDel;
	}

	public Integer getOpentimer() {
		return opentimer;
	}

	public void setOpentimer(Integer opentimer) {
		this.opentimer = opentimer;
	}

	public Integer getLogicDel() {
		return logicDel;
	}

	public void setLogicDel(Integer logicDel) {
		this.logicDel = logicDel;
	}

}

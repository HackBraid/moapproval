package com.longfor.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流程信息。
 */
@Entity
@Table(name = "FLOWDATA")
public class FlowData {

	/** id */
	private Integer id;

	/** 流程编号 */
	private String flowNo;

	/** 系统编号 */
	private String systemNo;

	/** 标题 */
	private String title;

	/** 发布人用户名 */
	private String pubUsername;

	/** 业务类型 */
	private String businessType;

	/** 流程状态 0:未完成,1:已完成,2:已取消 */
	private Integer flowStatus;

	/** 流程信息json */
	private String flowData;

	/** 创建时间 */
	private Date createTime;

	/** 更新时间 */
	private Date updateTime;

	/** 步骤id */
	private String runnote_id;

	/** 发布人真实姓名 */
	private String pubTrueName;
	
	/** 是否是滑动删除 0为删除 */
	private Integer slideDel;

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

	public Integer getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(Integer flowStatus) {
		this.flowStatus = flowStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFlowData() {
		return flowData;
	}

	public void setFlowData(String flowData) {
		this.flowData = flowData;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
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

	public String getRunnote_id() {
		return runnote_id;
	}

	public void setRunnote_id(String runnote_id) {
		this.runnote_id = runnote_id;
	}

	public String getPubTrueName() {
		return pubTrueName;
	}

	public void setPubTrueName(String pubTrueName) {
		this.pubTrueName = pubTrueName;
	}

	public Integer getSlideDel() {
		return slideDel;
	}

	public void setSlideDel(Integer slideDel) {
		this.slideDel = slideDel;
	}
}

package com.longfor.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 业务信息。
 */
@Entity
@Table(name = "BUSINESSDATA")
public class BusinessData {

	/** id */
	private Integer id;

	/** 系统编号 */
	private String systemNo;

	/** 流程编号 */
	private String flowNo;

	/** 发布人用户名 */
	private String pubUsername;

	/** 业务类型 */
	private String bznsType;

	/** 业务信息json */
	private String bznsData;
	
	/** 审批人 */
	private String appUsername;

	/** 创建时间 */
	private Date createTime;

	/** 更新时间 */
	private Date updateTime;
	
	/** 合同编号 */
	private String contractNo;

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

	public String getPubUsername() {
		return pubUsername;
	}

	public void setPubUsername(String pubUsername) {
		this.pubUsername = pubUsername;
	}

	public String getBznsType() {
		return bznsType;
	}

	public void setBznsType(String bznsType) {
		this.bznsType = bznsType;
	}

	public String getBznsData() {
		return bznsData;
	}

	public void setBznsData(String bznsData) {
		this.bznsData = bznsData;
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

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getSystemNo() {
		return systemNo;
	}

	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}

	public String getAppUsername() {
		return appUsername;
	}

	public void setAppUsername(String appUsername) {
		this.appUsername = appUsername;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
}

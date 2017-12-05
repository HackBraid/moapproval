package com.longfor.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 待办信息。
 */
@Entity
@Table(name = "SYSTEMURL")
public class SystemUrl {

	/** id */
	private Integer id;

	/** 系统编号 */
	private String systemNo;

	/** 业务类型 */
	private String businessType;

	/** 系统url */
	private String url;

	/** 接口类型 */
	private String interfacType;

	/** 接口调用状态 */
	private Integer status;


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

	public String getSystemNo() {
		return systemNo;
	}

	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInterfacType() {
		return interfacType;
	}

	public void setInterfacType(String interfacType) {
		this.interfacType = interfacType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

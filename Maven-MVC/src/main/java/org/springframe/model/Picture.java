package org.springframe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframe.base.Base;

@Entity
@Table(name = "PICTURE")
public class Picture extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 图片URL地址
	 */
	private String imgUrl;
	
	/**
	 * 图片类型
	 */
	private Integer imgType;

	@Column(name = "imgUrl", columnDefinition = "varchar(36)")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Column(name = "imgType", columnDefinition = "int(1)")
	public Integer getImgType() {
		return imgType;
	}

	public void setImgType(Integer imgType) {
		this.imgType = imgType;
	}

	public Picture() {
		super();
	}

	
	public Picture(String id, String imgUrl, Integer imgType) {
		super(id);
		this.imgUrl = imgUrl;
		this.imgType = imgType;
	}

}

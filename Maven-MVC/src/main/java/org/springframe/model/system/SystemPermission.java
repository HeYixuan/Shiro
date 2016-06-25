/**
 * 
 */
package org.springframe.model.system;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统权限
 * 
 * @author 340819
 *
 */
@Entity
@Table(name = "SYSTEM_PERMISSION")
public class SystemPermission implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private String id;

	/**
	 * 权限名称
	 */
	private String permisionName;

	/**
	 * 一个角色对应多个权限,反之一个权限对应多个角色
	 */
	private Set<SystemRole> roles = new HashSet<SystemRole>();

	/**
	 * URL地址
	 */
	private String url;

	/**
	 * 图标
	 */
	private String icon;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator", strategy = GenerationType.AUTO)
	@Column(name = "ID", columnDefinition = "VARCHAR(36)", unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "PERMISION_NAME", columnDefinition = "VARCHAR(36)")
	public String getPermisionName() {
		return permisionName;
	}

	public void setPermisionName(String permisionName) {
		this.permisionName = permisionName;
	}

	@Column(name = "ICON", columnDefinition = "VARCHAR(500)")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "URL", columnDefinition = "VARCHAR(500)")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@ManyToMany
	public Set<SystemRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SystemRole> roles) {
		this.roles = roles;
	}

	/******************************* 构造方法 ************************************/

	public SystemPermission() {
		super();
	}

	public SystemPermission(String id, String permisionName, Set<SystemRole> roles, String url, String icon) {
		super();
		this.id = id;
		this.permisionName = permisionName;
		this.roles = roles;
		this.url = url;
		this.icon = icon;
	}

	@Transient
	public Set<String> getRoleName() {
		Set<String> set = new HashSet<String>();
		Set<SystemRole> roles = getRoles();
		for (SystemRole role : roles) {
			set.add(role.getRoleName());
		}
		return set;
	}
}

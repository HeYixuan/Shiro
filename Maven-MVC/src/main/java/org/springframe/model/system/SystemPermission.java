/**
 * 
 */
package org.springframe.model.system;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
	 * 父节点ID
	 */
	private SystemPermission pid;
		
	/**
	 * 资源类型
	 * 0菜单
	 * 1按钮
	 */
	private Integer resourceType;
	
	/**
	 * 状态 
	 * 0启用
	 * 1停用
	 */
	private Integer status;
	
	/**
	 * 图标
	 */
	private String icon;
	
	/**
	 * 菜单路径
	 */
	private String url;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 一个角色对应多个权限,反之一个权限对应多个角色
	 */
	private Set<SystemRole> roles = new HashSet<SystemRole>();
	
	/**
	 * 子节点
	 */
	private Set<SystemPermission> children = new HashSet<SystemPermission>();


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
	
	@Column(name = "PID", columnDefinition = "VARCHAR(36)")
	public SystemPermission getPid() {
		return pid;
	}

	public void setPid(SystemPermission pid) {
		this.pid = pid;
	}
	
	@Column(name = "TYPE", columnDefinition = "int(1)")
	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name = "STATUS", columnDefinition = "int(1)")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	
	@Column(name = "SORT", columnDefinition = "int(1)")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Column(name = "DESCRIPTION", columnDefinition = "VARCHAR(36)")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(mappedBy="permissions")
	public Set<SystemRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SystemRole> roles) {
		this.roles = roles;
	}
	
	@OneToMany(mappedBy="pid",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public Set<SystemPermission> getChildren() {
		return children;
	}

	public void setChildren(Set<SystemPermission> children) {
		this.children = children;
	}


	/******************************* 构造方法 ************************************/

	public SystemPermission() {
		super();
	}
	
	

	public SystemPermission(String id, String permisionName, SystemPermission pid, Integer resourceType, Integer status,
			String icon, String url, Integer sort, String description, Set<SystemRole> roles,
			Set<SystemPermission> children) {
		super();
		this.id = id;
		this.permisionName = permisionName;
		this.pid = pid;
		this.resourceType = resourceType;
		this.status = status;
		this.icon = icon;
		this.url = url;
		this.sort = sort;
		this.description = description;
		this.roles = roles;
		this.children = children;
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

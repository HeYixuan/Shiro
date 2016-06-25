/**
 * 
 */
package org.springframe.model.system;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统角色
 * 
 * @author 340819
 *
 */
@Entity
@Table(name = "SYSTEM_ROLE")
public class SystemRole implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private String id;
	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 一个角色对应多个用户
	 */
	private Set<SystemUser> systemUsers = new HashSet<SystemUser>();
	
	/**
	 * 一个角色对应多个权限
	 */
	private Set<SystemPermission> permissions = new HashSet<SystemPermission>();

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

	@Column(name = "ROLE_NAME", columnDefinition = "VARCHAR(36)")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@ManyToMany(mappedBy="roles")
	public Set<SystemUser> getSystemUsers() {
		return systemUsers;
	}

	public void setSystemUsers(Set<SystemUser> systemUsers) {
		this.systemUsers = systemUsers;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "SYSTEM_ROLE_PERMISSION", joinColumns = { @JoinColumn(name = "SYSTEM_ROLE_ID") },
	inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID") })
	public Set<SystemPermission> getPermissions() {
		return permissions;
	}
	
	public void setPermissions(Set<SystemPermission> permissions) {
		this.permissions = permissions;
	}
	
	
	
	/******************************* 构造方法 ************************************/
	
	public SystemRole() {
		super();
	}

	public SystemRole(String id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}
	
	@Transient
	public Set<String> getPermissionsName() {
		Set<String> set = new HashSet<String>();
		Set<SystemPermission> permissions = getPermissions();
		for (SystemPermission systemPermission : permissions) {
			set.add(systemPermission.getPermisionName());
		}
		return set;
	}

	@Transient
	public Set<String> getUserName() {
		Set<String> set = new HashSet<String>();
		Set<SystemUser> systemUsers = getSystemUsers();
		for (SystemUser systemUser : systemUsers) {
			set.add(systemUser.getUsername());
		}
		return set;
	}
	
	
	/*
	 * public List<SystemPermission> getPermissions() { return permissions; }
	 * public void setPermissions(List<SystemPermission> permissions) {
	 * this.permissions = permissions; }
	 */
	/*
	 * public List<SystemUser> getSystemUsers() { return systemUsers; } public
	 * void setSystemUsers(List<SystemUser> systemUsers) { this.systemUsers =
	 * systemUsers; }
	 */
	/*
	 * public List<String> getPermissionsName(){ List<String> list = new
	 * ArrayList<String>(); List<SystemPermission> permissions =
	 * getPermissions(); for (SystemPermission systemPermission : permissions) {
	 * list.add(systemPermission.getPermisionName()); } return list; }
	 */
}

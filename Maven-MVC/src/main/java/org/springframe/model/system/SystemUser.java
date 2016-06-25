/**
 * 
 */
package org.springframe.model.system;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframe.base.Base;

/**
 * 系统用户
 * 
 * @author 何壹轩
 *
 */
@Entity
@Table(name = "SYSTEM_USER")
public class SystemUser extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 电子邮箱
	 */
	private String email;

	/**
	 * 一个用户对应多个角色
	 */
	private Set<SystemRole> roles = new HashSet<SystemRole>();

	/**
	 * 是否启用 启用为true
	 */
	private boolean isEnabled;

	@Column(name = "FirstName", columnDefinition = "varchar(36)")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LastName", columnDefinition = "varchar(36)")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "UserName", columnDefinition = "varchar(36)")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "Password", columnDefinition = "varchar(36)")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "Email", columnDefinition = "varchar(36)")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "isEnabled", columnDefinition = "boolean")
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "SYSTEM_USER_ROLE", joinColumns = { @JoinColumn(name = "systemUserID") }, inverseJoinColumns = {
			@JoinColumn(name = "systemRoleID") })
	public Set<SystemRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SystemRole> roles) {
		this.roles = roles;
	}

	@Transient
	public Set<String> getRoleName() {
		Set<SystemRole> systemRoles = getRoles();
		Set<String> set = new HashSet<String>();
		for (SystemRole role : systemRoles) {
			set.add(role.getRoleName());
		}
		return set;
	}

	/******************************* 构造方法 ************************************/
	public SystemUser() {
		super();
	}

	public SystemUser(String firstName, String lastName, String username, String password, String email,
			boolean isEnabled) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.isEnabled = isEnabled;
	}

}

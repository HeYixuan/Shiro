package org.springframe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframe.base.Base;

@Entity
@Table(name = "USERS")
public class Users extends Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7206952154451015017L;
	
	private Long sid;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}

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
	 * 邮箱
	 */
	private String email;
	/**
	 * 是否激活
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
	@Column(name = "Password", columnDefinition = "varchar(500)")
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
	
	
	public Users() {
		super();
	}
	
	public Users(String id, String username, String password, String email, boolean isEnabled) {
		super(id);
		this.username = username;
		this.password = password;
		this.email = email;
		this.isEnabled = isEnabled;
	}
	
}

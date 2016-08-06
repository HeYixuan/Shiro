package org.springframe.model;

import java.util.Date;

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
	private int isEnabled;
	
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
	
	@Column(name = "isEnable", columnDefinition = "int(1)")
	public int isEnabled() {
		return isEnabled;
	}
	public void setEnabled(int isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	
	public Users() {
		super();
	}
	public Users(String username, String password, String email, int isEnabled) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.isEnabled = isEnabled;
	}
	
}

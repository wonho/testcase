package com.base.business.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String userName;
	String passwd;
	String dept;
	String pos;
	
	List<SimpleGrantedAuthority> roleList = new ArrayList<SimpleGrantedAuthority>();
	
	public User(String userName, String passwd,List<SimpleGrantedAuthority> roleList) {
		this.userName = userName;
		this.passwd = passwd;
		this.roleList = roleList;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roleList;
	}

	@Override
	public String getPassword() {
		return this.passwd;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}


	public String getPos() {
		return pos;
	}


	public void setPos(String pos) {
		this.pos = pos;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", passwd=" + passwd + ", dept=" + dept + ", pos=" + pos + ", roleList="
				+ roleList + "]";
	}
}

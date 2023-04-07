package com.code.springdemo.entity;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="authorities")
public class Authorities {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="authorities_no")
	private int authorities_no;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="authority")
	private String authority;
	
	public Authorities() {
		
	}
	
	public int getAuthorities_no() {
		return authorities_no;
	}

	public void setAuthorities_no(int authorities_no) {
		this.authorities_no = authorities_no;
	}

	public String getuserName() {
		return userName;
	}


	public void setuserName(String userName) {
		this.userName = userName;
	}


	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}


	@Override
	public String toString() {
		return "Authorities [authorities_no=" + authorities_no+", userName=" + userName + ", authority=" + authority + "]";
	}


}
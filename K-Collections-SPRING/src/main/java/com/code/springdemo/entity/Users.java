package com.code.springdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class Users {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_no")
	private int userNo;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="user_real_name")
	private String userRealName;
	
	@Column(name="user_purchase_amount")
	private int userPurchaseAmount;
	
	@Column(name="user_category_no")
	private int userCategoryNo;
	
	@Column(name="enabled")
	private boolean enAbled;

	public Users() {
		
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = "{noop}"+password;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public int getUserPurchaseAmount() {
		return userPurchaseAmount;
	}

	public void setUserPurchaseAmount(int userPurchaseAmount) {
		this.userPurchaseAmount = userPurchaseAmount;
	}

	public int getUserCategoryNo() {
		return userCategoryNo;
	}

	public void setUserCategoryNo(int userCategoryNo) {
		this.userCategoryNo = userCategoryNo;
	}
	
	public boolean isEnAbled() {
		return enAbled;
	}

	public void setEnAbled(boolean enAbled) {
		this.enAbled = enAbled;
	}

	@Override
	public String toString() {
		return "User [userNo=" + userNo + ", user_name=" + userName + ", password=" + password + ", user_real_name=" + userRealName
				+ ", userPurchaseAmount=" + userPurchaseAmount + ", userCategoryNo=" + userCategoryNo + ", enAbled=" + enAbled + "]";
	}


		
}
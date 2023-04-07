package com.code.springdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cart")
public class Cart {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cart_no")
	private int cartNo;
	
	@Column(name="cart_user_no")
	private int cartUserNo;
	
	@Column(name="cart_artwork_no")
	private int cartArtworkNo;
	

	public Cart() {
		
	}


	public int getCartNo() {
		return cartNo;
	}


	public void setCartNo(int cartNo) {
		this.cartNo = cartNo;
	}


	public int getCartUserNo() {
		return cartUserNo;
	}


	public void setCartUserNo(int cartUserNo) {
		this.cartUserNo = cartUserNo;
	}


	public int getCartArtworkNo() {
		return cartArtworkNo;
	}


	public void setCartArtworkNo(int cartArtworkNo) {
		this.cartArtworkNo = cartArtworkNo;
	}


	@Override
	public String toString() {
		return "Cart [cartNo=" + cartNo + ", cartUserNo=" + cartUserNo + ", cartArtworkNo=" + cartArtworkNo + "]";
	}


}

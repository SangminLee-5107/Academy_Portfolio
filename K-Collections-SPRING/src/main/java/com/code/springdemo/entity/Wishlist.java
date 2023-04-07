package com.code.springdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wishlist")
public class Wishlist {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="wishlist_no")
	private int wishlistNo;
	
	@Column(name="wishlist_user_no")
	private int wishlistUserNo;
	
	@Column(name="wishlist_artwork_no")
	private int wishlistArtworkNo;
	

	public Wishlist() {
		
	}
	
	public Wishlist(int wishlist_artwork_no) {
        this.wishlistArtworkNo = wishlist_artwork_no;
    }


	public int getWishlistNo() {
		return wishlistNo;
	}


	public void setWishlistNo(int wishlistNo) {
		this.wishlistNo = wishlistNo;
	}


	public int getWishlistUserNo() {
		return wishlistUserNo;
	}


	public void setWishlistUserNo(int wishlistUserNo) {
		this.wishlistUserNo = wishlistUserNo;
	}


	public int getWishlistArtworkNo() {
		return wishlistArtworkNo;
	}


	public void setWishlistArtworkNo(int wishlistArtworkNo) {
		this.wishlistArtworkNo = wishlistArtworkNo;
	}


	@Override
	public String toString() {
		return "Wishlist [wishlistNo=" + wishlistNo + ", wishlistUserNo=" + wishlistUserNo + ", wishlistArtworkNo="
				+ wishlistArtworkNo + "]";
	}


}
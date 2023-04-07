package com.code.springdemo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="artwork")
public class Artwork {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="artwork_no")
	private int artworkNo;
	
	@Column(name="artwork_name")
	private String artworkName;
	
	@Column(name="artwork_artist_no")
	private int artworkArtistNo;
	
	@Column(name="artwork_category_no")
	private int artworkCategoryNo;
	
	@Column(name="artwork_price")
	private int artworkPrice;
	
	@Column(name="artwork_regdate")
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime artworkRegdate;
	
	@Column(name="artwork_sold")
	private boolean artworkSold;
	
	@Column(name="artwork_level")
	private String artworkLevel;
	
	@Column(name="artwork_filename")
	private String artworkFilename;
	
	public Artwork(){}

	public int getArtworkNo() {
		return artworkNo;
	}

	public void setArtworkNo(int artworkNo) {
		this.artworkNo = artworkNo;
	}

	public String getArtworkName() {
		return artworkName;
	}

	public void setArtworkName(String artworkName) {
		this.artworkName = artworkName;
	}

	public int getArtworkArtistNo() {
		return artworkArtistNo;
	}

	public void setArtworkArtistNo(int artworkArtistNo) {
		this.artworkArtistNo = artworkArtistNo;
	}

	public int getArtworkCategoryNo() {
		return artworkCategoryNo;
	}

	public void setArtworkCategoryNo(int artworkCategoryNo) {
		this.artworkCategoryNo = artworkCategoryNo;
	}

	public int getArtworkPrice() {
		return artworkPrice;
	}

	public void setArtworkPrice(int artworkPrice) {
		this.artworkPrice = artworkPrice;
	}

	public LocalDateTime getArtworkRegdate() {
		return artworkRegdate;
	}

	public void setArtworkRegdate(LocalDateTime artworkRegdate) {
		this.artworkRegdate = artworkRegdate;
	}

	public boolean isArtworkSold() {
		return artworkSold;
	}

	public void setArtworkSold(boolean artworkSold) {
		this.artworkSold = artworkSold;
	}

	public String getArtworkLevel() {
		return artworkLevel;
	}

	public void setArtworkLevel(String artworkLevel) {
		this.artworkLevel = artworkLevel;
	}

	public String getArtworkFilename() {
		return artworkFilename;
	}

	public void setArtworkFilename(String artworkFilename) {
		this.artworkFilename = artworkFilename;
	}

	@Override
	public String toString() {
		return "Artwork [artworkNo=" + artworkNo + ", artworkName=" + artworkName + ", artworkArtistNo="
				+ artworkArtistNo + ", artworkCategoryNo=" + artworkCategoryNo + ", artworkPrice=" + artworkPrice
				+ ", artworkRegdate=" + artworkRegdate + ", artworkSold=" + artworkSold + ", artworkLevel="
				+ artworkLevel + ", artworkFilename=" + artworkFilename + "]";
	}
	
	
}

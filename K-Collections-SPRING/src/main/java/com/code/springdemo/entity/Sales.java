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
@Table(name="sales")
public class Sales {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sales_no")
	private int salesNo;
	
	@Column(name="sales_user_no")
	private int salesUserNo;
	
	@Column(name="sales_artwork_no")
	private int salesArtworkNo;
	
	@Column(name="sales_artwork_price")
	private int salesArtworkPrice;

	@Column(name="sales_purchase_time")
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime salesPurchaseTime;
	
	public Sales() {
		
	}

	public int getSalesNo() {
		return salesNo;
	}

	public void setSalesNo(int salesNo) {
		this.salesNo = salesNo;
	}

	public int getSalesUserNo() {
		return salesUserNo;
	}

	public void setSalesUserNo(int salesUserNo) {
		this.salesUserNo = salesUserNo;
	}

	public int getSalesArtworkNo() {
		return salesArtworkNo;
	}

	public void setSalesArtworkNo(int salesArtworkNo) {
		this.salesArtworkNo = salesArtworkNo;
	}
	
	public int getSalesArtworkPrice() {
		return salesArtworkPrice;
	}

	public void setSalesArtworkPrice(int salesArtworkPrice) {
		this.salesArtworkPrice = salesArtworkPrice;
	}

	public LocalDateTime getSalesPurchaseTime() {
		return salesPurchaseTime;
	}

	public void setSalesPurchaseTime(LocalDateTime salesPurchaseTime) {
		this.salesPurchaseTime = salesPurchaseTime;
	}

	@Override
	public String toString() {
		return "Sales [salesNo=" + salesNo + ", salesUserNo=" + salesUserNo + ", salesArtworkNo=" + salesArtworkNo
				+ ", salesPurchaseTime=" + salesPurchaseTime + "]";
	}


}

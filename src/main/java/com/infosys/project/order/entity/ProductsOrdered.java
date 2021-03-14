package com.infosys.project.order.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productsordered")
@IdClass(value=ProductsOrderedIdusingIdClass.class)
@Data
@NoArgsConstructor
public class ProductsOrdered {
	@Id
	private int orderid;
	@Id
	private int prodid;
	@Column(nullable = false)
	private int sellerid;
	@Column(nullable = false)
	private int quantity;
	@Column(nullable = false)
	private Double price;
	@Column(nullable = false)
	private String status;
	
	
	
	
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public ProductsOrdered() {
	super();
      }
	
	public int getProdid() {
		return prodid;
	}

	public void setProdid(int prodid) {
		this.prodid = prodid;
	}

//	public int getBuyerid() {
//		return buyerid;
//	}
//
//	public void setBuyerid(int buyerid) {
//		this.buyerid = buyerid;
//	}

	public int getSellerid() {
		return sellerid;
	}
	public void setSellerid(int sellerid) {
		this.sellerid = sellerid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}

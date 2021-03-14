package com.infosys.project.order.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.infosys.project.order.entity.OrderDetails;
import com.infosys.project.order.entity.ProductsOrdered;

public class CombinedDTO {
	private int orderid;
	private int buyerid;
	private double amount;
	private Date date;
	private String address;
	private String status;
	List<ProductsOrderedDTO> productsOrdered;
	
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(int buyerid) {
		this.buyerid = buyerid;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<ProductsOrderedDTO> getProductsOrdered() {
		return productsOrdered;
	}
	public void setProductsOrdered(List<ProductsOrderedDTO> productsOrdered) {
		this.productsOrdered = productsOrdered;
	}
	//converts entity to dto
	public static CombinedDTO valueOf(OrderDetails orderDetails) {
		CombinedDTO orderDTO=new CombinedDTO();
		orderDTO.setOrderid(orderDetails.getOrderid());
		orderDTO.setBuyerid(orderDetails.getBuyerid());
		orderDTO.setAmount(orderDetails.getAmount());
		orderDTO.setDate(orderDetails.getDate());
		orderDTO.setAddress(orderDetails.getAddress());
		orderDTO.setStatus( orderDetails.getStatus());
		return orderDTO;
	}
}

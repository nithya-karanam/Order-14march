package com.infosys.project.order.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.infosys.project.order.entity.OrderDetails;
import com.infosys.project.order.entity.ProductsOrdered;

public class OrderDTO {

	private int orderid;
	private int buyerid;
	private Double amount;
	private Date date;
	private String address;
	private String status;
	List<ProductsOrdered> prodOrdered= new ArrayList<ProductsOrdered>();


	public List<ProductsOrdered> getProdOrdered() {
		return prodOrdered;
	}

	public void setProdOrdered(List<ProductsOrdered> prodOrdered) {
		this.prodOrdered = prodOrdered;
	}
	public OrderDTO() {
		super();
	}

	public OrderDTO(int orderid, int buyerid, double amount,Date date, String address,String status) {
		this();
		this.orderid=orderid;
		this.buyerid=buyerid;
		this.date=date;
		this.address=address;
		this.status=status;
	}
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


	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
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
	// Converts Entity into DTO
	public static OrderDTO valueOf(OrderDetails orderDetails) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setOrderid(orderDetails.getOrderid());
		orderDTO.setBuyerid(orderDetails.getBuyerid());
		orderDTO.setAmount(orderDetails.getAmount());
		orderDTO.setDate(orderDetails.getDate());
		orderDTO.setAddress(orderDetails.getAddress());
		orderDTO.setStatus( orderDetails.getStatus());
		 
		return orderDTO;
	}
	//converts DTO to entity
	public OrderDetails createEntity() {
		OrderDetails order = new OrderDetails();
		order.setOrderid(this.orderid);
		order.setBuyerid(this.buyerid);
		order.setAmount(this.amount);
		order.setDate(this.date);
        order.setAddress(this.address);
        order.setStatus(this.status);
		return order;
	}

//	@Override
//	public String toString() {
//		return "OrderDTO [orderid=" + orderid + ", buyerid=" + buyerid + ", amount=" + amount
//				+ ", date=" + date + ",address=" + address +", status=" + status +"]";
//	}

}

package com.infosys.project.order.dto;

import java.math.BigDecimal;

import com.infosys.project.order.entity.OrderDetails;
import com.infosys.project.order.entity.ProductsOrdered;

public class ProductsOrderedDTO {
private int orderid;
private int prodid;
private int sellerid;
private int quantity;
private String status;
private double price;

   public ProductsOrderedDTO() {
	   super();
   }
   public ProductsOrderedDTO(int orderid,int prodid, int sellerid, int quantity, String status, double price) {
	 this.orderid=orderid;
	   this.prodid=prodid;
	   this.sellerid=sellerid;
	   this.quantity=quantity;
	   this.status=status;
	   this.price=price;
	  
   }
   
public int getOrderid() {
	return orderid;
}
public void setOrderid(int orderid) {
	this.orderid = orderid;
}
public int getProdid() {
	return prodid;
}
public void setProdid(int prodid) {
	this.prodid = prodid;
}
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
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
//coverts Entity to DTO
public static ProductsOrderedDTO valueOf(ProductsOrdered productDetails) {
	ProductsOrderedDTO porderedDTO = new ProductsOrderedDTO();
	porderedDTO.setOrderid(productDetails.getOrderid());
	porderedDTO.setProdid(productDetails.getProdid());
	porderedDTO.setSellerid(productDetails.getSellerid());
	porderedDTO.setQuantity(productDetails.getQuantity());
	porderedDTO.setStatus(productDetails.getStatus());
	porderedDTO.setPrice(productDetails.getPrice());
	return porderedDTO;
}
   //Dto to entity
public  ProductsOrdered createEntity() {
	ProductsOrdered prod=new ProductsOrdered();
	prod.setOrderid(this.getOrderid());
	prod.setProdid(this.getProdid());
	prod.setPrice(this.getPrice());
	prod.setPrice(this.getPrice());
	prod.setQuantity(this.getQuantity());
	prod.setSellerid(this.getSellerid());
	prod.setStatus("ORDER PLACED");
	return prod;
	
}
}

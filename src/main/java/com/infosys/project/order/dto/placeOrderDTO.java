package com.infosys.project.order.dto;

public class placeOrderDTO {
private int buyerId;
private String address;
private int prodid;

public int getProdid() {
	return prodid;
}
public void setProdid(int prodid) {
	this.prodid = prodid;
}
public int getBuyerId() {
	return buyerId;
}
public void setBuyerId(int buyerId) {
	this.buyerId = buyerId;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}



}

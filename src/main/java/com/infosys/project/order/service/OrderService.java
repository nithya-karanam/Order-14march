package com.infosys.project.order.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.project.order.repository.OrderRepository;
import com.infosys.project.order.repository.ProductsOrderedRepo;
import com.infosys.project.order.dto.CartDTO;
import com.infosys.project.order.dto.CombinedDTO;
import com.infosys.project.order.dto.OrderDTO;
import com.infosys.project.order.dto.ProductsDTO;
import com.infosys.project.order.dto.ProductsOrderedDTO;
import com.infosys.project.order.dto.newPlaceOrder;
import com.infosys.project.order.dto.placeOrderDTO;
import com.infosys.project.order.entity.OrderDetails;
import com.infosys.project.order.entity.ProductsOrdered;
import com.infosys.project.order.entity.ProductsOrderedIdusingIdClass;

@Service
public class OrderService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OrderRepository orderRepo;
	@Autowired
	ProductsOrderedRepo productRepo;
	@Autowired
	KafkaConsumer kafka;
	
	public CombinedDTO getSpecificOrderDetails(int orderid) {
		CombinedDTO newDTO=null;
		Optional<OrderDetails> optOrder=orderRepo.findById(orderid);
		if(optOrder.isPresent()) {
			OrderDetails orderDetails=optOrder.get();
			newDTO=CombinedDTO.valueOf(orderDetails);
			List<ProductsOrdered> prodorders=productRepo.findByorderid(orderid);
		List<ProductsOrderedDTO> prodList=new ArrayList<>();
		for(ProductsOrdered p:prodorders) {
			ProductsOrderedDTO pdto=ProductsOrderedDTO.valueOf(p);
			prodList.add(pdto);
		}
		newDTO.setProductsOrdered(prodList);
		}
		return newDTO;
	}
	public OrderDTO getSpecificOrder(int orderid) {
		logger.info("Order details : {}", orderid);
		OrderDTO orderDTO=null;
		Optional<OrderDetails> optOrder=orderRepo.findById(orderid);
		if(optOrder.isPresent()) {
			OrderDetails orderDetails=optOrder.get();
	        orderDTO=OrderDTO.valueOf(orderDetails);
		}
		return orderDTO;
	}
   public Double placeOrder(CartDTO cd,List<ProductsDTO> prodlist) {
	double cost=0.0;
		for(ProductsDTO pd:prodlist) {
			  if(cd.getProdId().equals(pd.getProdid())) {
				    cost+=cd.getQuantity()*pd.getPrice();
				    System.out.println("cost");
				  
			  }
		}
	return cost;
}
   public void toDatabase(newPlaceOrder placeOrder,CartDTO cart,List<ProductsDTO> prodlist, double totalAmount) {
	  Date date=new Date();
	  
	  OrderDetails order=new OrderDetails();
	      order.setBuyerid(placeOrder.getBuyerid());
	      order.setAmount(totalAmount);
	      order.setAddress(placeOrder.getAddress());
	      order.setDate(date);
	      order.setStatus("ORDER PLACED");
	        OrderDetails or= orderRepo.save(order);
	         
	  ProductsOrdered products=new ProductsOrdered();       
		  for(ProductsDTO prod:prodlist) {
			     if(cart.getProdId().equals(prod.getProdid())) {
			    	 System.out.println("dbstart");
			    	 products.setOrderid(or.getBuyerid());
			    	  products.setOrderid(or.getOrderid());
			    	  products.setProdid(prod.getProdid());
			    	  products.setPrice(prod.getPrice());
			    	  products.setSellerid(prod.getSellerid());
			    	  products.setQuantity(cart.getQuantity());
			    	  products.setStatus("ORDER PLACED");
			    	 
			    	  productRepo.save(products);
			     }
			     
			     }
		     }

    public void reOrder(Integer orderId,Integer prodId) throws Exception{
	     Date date=new Date();
	     Optional<OrderDetails> order=orderRepo.findById(orderId);
	     Optional<ProductsOrdered> products= productRepo.findById(new ProductsOrderedIdusingIdClass(orderId, prodId));
	     OrderDTO orderDto=null;
	     OrderDetails orderEntity=null;
	     ProductsOrdered prodentity=null;
	     ProductsOrderedDTO pdto=null;
	     OrderDTO reorderDTO;
	     if(order.isPresent()) {
	    	   OrderDetails entity=order.get();
	    	   System.out.println(entity.getOrderid());
	    	   orderDto=OrderDTO.valueOf(entity);
	    	   System.out.println(orderDto.getOrderid());
	    	   if(orderId.equals(orderDto.getOrderid())) {
	    		   orderEntity=new OrderDetails();
	    		   orderEntity.setBuyerid(orderDto.getBuyerid());
                   orderEntity.setDate(date);
                   orderEntity.setAddress(orderDto.getAddress());
                   orderEntity.setAmount(orderDto.getAmount());
                   orderEntity.setStatus("ORDER PLACED");
                   orderRepo.save(orderEntity);
                   reorderDTO=OrderDTO.valueOf(orderEntity);
	    	   }
	    	   else {
	    		   throw new Exception("NO_BUYER_FOUND");
	    	   }
	    	   if(products.isPresent()) {
	    		   System.out.println(reorderDTO.getOrderid());
	    	   ProductsOrdered pentity=products.get();
	    	   pdto=ProductsOrderedDTO.valueOf(pentity);
	    	   if(prodId.equals(pdto.getProdid())) {
	    		   prodentity=new ProductsOrdered();
	    		   prodentity.setOrderid(reorderDTO.getOrderid());
	    		   prodentity.setProdid(prodId);
	    		   prodentity.setQuantity(pdto.getQuantity());
	    		   prodentity.setPrice(pdto.getPrice());
	    		   prodentity.setSellerid(pdto.getSellerid());
	    		   prodentity.setStatus("ORDER PLACED");
	    		   productRepo.save(prodentity);
	    	   }
	    	   else
	    		   throw new Exception("NO_PRODUCT_FOUND");
	    	   }
	    	   
	     }
	     else {
	    	 throw new Exception("No_ORDER");
	     }     
}
    
    public CartDTO getCart() {
    	String message=kafka.getMsg();
 
        String[] words=message.split("\\s");
        	Integer numbers[]=new Integer[3];
        		numbers[0]=Integer.parseInt(words[0]);//buyerId
        		numbers[1]=Integer.parseInt(words[1]);//prodId
        		numbers[2]=Integer.parseInt(words[2]);//quantity
        	CartDTO cart =new CartDTO();
        	cart.setBuyerId(numbers[0]);
        	cart.setProdId(numbers[1]);
        	cart.setQuantity(numbers[2]);
        	return cart;
    	
    }
   
    
public String changeOrderStatus(Integer orderid,String status){
	String orderstatus=null;
	 List<OrderDetails> entities=orderRepo.findAll();
	 for(OrderDetails entity:entities) {
		 if(orderid.equals(entity.getOrderid())) {
			 entity.setStatus(status);
			 orderRepo.save(entity);
			 orderstatus="Status of your order:"+status;
			 return orderstatus;
		 }
	 }
	 orderstatus ="Sorry!!!No such order is present to change the status..Please Confirm Your Order";
	 return orderstatus;
}

}

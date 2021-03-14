package com.infosys.project.order.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.project.order.dto.CartDTO;
import com.infosys.project.order.dto.CombinedDTO;
import com.infosys.project.order.dto.OrderDTO;
import com.infosys.project.order.dto.ProductsDTO;
import com.infosys.project.order.dto.ProductsOrderedDTO;
import com.infosys.project.order.dto.newPlaceOrder;
import com.infosys.project.order.dto.placeOrderDTO;
import com.infosys.project.order.service.KafkaConsumer;
import com.infosys.project.order.service.OrderService;



@RestController
@CrossOrigin
public class OrderController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	Environment environment;
	@Autowired
	OrderService orderService;
	@Autowired
	KafkaConsumer kafka;
	
	@Value("${cart.uri}")
	String carturl;

	// Fetches order details of a specific order
	@GetMapping(value = "/orders/{orderid}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public CombinedDTO getspecificOrderDetails(@PathVariable int orderid) {
		logger.info("Orderdetails request for user {}", orderid);

		return orderService.getSpecificOrderDetails(orderid);
	}
	
	
	//Removes products from the cart
      @DeleteMapping(value="/cart/delete/{buyerId}/{prodId}")
     public void delete(@PathVariable Integer buyerId,@PathVariable Integer prodId) {
	 String url="http://localhost:8300/cart/remove/"+buyerId+"/"+prodId;
	 RestTemplate restTemp=new RestTemplate();
	 restTemp.delete(url);
}
//To place new order
	@SuppressWarnings("unchecked")
	@PostMapping(value="/order/placeOrder",consumes = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<String> placeOrder(@RequestBody newPlaceOrder placeorderDTO) throws JsonParseException, IOException{
     ResponseEntity<String> response = null;
      	try {

		ObjectMapper mapper =new ObjectMapper();
		RestTemplate restTemp=new RestTemplate();
		//Fetching cartdetails using kafka
		CartDTO cartDto=orderService.getCart();
	
		//To fetch productdetails from ProductMs
		String urlprod="http://localhost:8200/api/products";
		String prodDto=restTemp.getForObject(urlprod, String.class);
		ProductsDTO[] prodlist1=mapper.readValue(prodDto, ProductsDTO[].class);
		List<ProductsDTO> prodDtolist=Arrays.asList(mapper.readValue(prodDto, ProductsDTO[].class));
		
		Double totalamount=orderService.placeOrder(cartDto,prodDtolist);
		
       // Updating the database		
		orderService.toDatabase(placeorderDTO, cartDto, prodDtolist, totalamount);	
		
		
		//delete prodct from cart
          delete(cartDto.getBuyerId(),cartDto.getProdId());
	
		String successMessage = environment.getProperty("ORDER_PLACED_SUCCESSFULLY");
		 response = new ResponseEntity<String>(successMessage,HttpStatus.CREATED);
		 
	           }catch(Exception e) {
	      	throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
			 
	      }
      	return response;	

	}
	
	// To reorder the placed order
	
	@SuppressWarnings("unchecked")
	@PostMapping(value="/order/reOrder/{orderId}/{prodId}")
	public ResponseEntity<String> reOrder(@PathVariable Integer orderId,@PathVariable Integer prodId) throws Exception {
         ResponseEntity<String> response=null;
       try {
		orderService.reOrder(orderId, prodId);
		 String successMessage = environment.getProperty("REORDER_PLACED_SUCCESSFULLY");
		 response = new ResponseEntity<String>(successMessage,HttpStatus.CREATED);
			 
	}catch(Exception e) {
		throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
			 
	}
	return response;
		
	}
	//To change the status of the order
	@PutMapping("order/{orderid}/{status}")
public String changeOrderStatus(@PathVariable Integer orderid,@PathVariable String status) {
		return orderService.changeOrderStatus(orderid, status);
	}
	@GetMapping(value="/kafka")
	public CartDTO kafka(){
		return orderService.getCart();
	}
	
	
}

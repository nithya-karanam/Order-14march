package com.infy.OrderMS;


import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.infosys.project.order.dto.OrderDTO;
import com.infosys.project.order.entity.OrderDetails;
import com.infosys.project.order.repository.OrderRepository;
import com.infosys.project.order.service.OrderService;

@SpringBootTest
class OrderMsApplicationTests {

	@InjectMocks
	private OrderService service=new OrderService();
	@Mock
	private OrderRepository orderRepo;
@Test
public void getSpecificOrderDetailsValid() throws Exception{
	OrderDetails or=new OrderDetails();
	or.setOrderid(1);
	//Mockito.when(orderRepo.findById(1)).thenReturn(order);
	
}
}

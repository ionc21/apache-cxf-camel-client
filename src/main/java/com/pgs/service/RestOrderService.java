package com.pgs.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.pgs.schema.order.OrderInquiryResponseType;
import com.pgs.schema.order.OrderInquiryType;

@Path("/service")
@Component("restOrderService")
public class RestOrderService {

	@POST
	@Path("/processOrders")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderInquiryResponseType processOrders(OrderInquiryType orderRequest) {
		return null;
	}
}

package com.pgs.routebuilder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import com.pgs.schema.order.OrderInquiryResponseType;
import com.pgs.schema.order.OrderInquiryType;
import com.pgs.service.OrderService;

@Component("soapRouteBuilder")
@ImportResource(value = "classpath:META-INF/spring/camel-cxf.xml")
public class SoapRouteBuilder extends RouteBuilder {

	@Autowired
	OrderService orderService;

	@Override
	public void configure() throws Exception {
		from("cxf:bean:orders").log("${body}").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				OrderInquiryType orderInquiry = exchange.getIn().getBody(OrderInquiryType.class);
				OrderInquiryResponseType response = orderService.processOrder(orderInquiry);
				exchange.getOut().setBody(response);
			}
		});
	}
}
package com.pgs.routebuilder;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component("restRouteExample")
public class RestRouteExample extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("cxf:bean:orders").log("BEFORE (XML) = ${body}").marshal().json()
				.to("cxfrs://http://localhost:9090?resourceClasses=com.pgs.service.OrderService&bindingStyle=SimpleConsumer");
	}
}
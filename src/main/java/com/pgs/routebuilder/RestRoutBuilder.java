package com.pgs.routebuilder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import com.pgs.schema.order.OrderInquiryType;

@Component("restRoutBuilder")
@ImportResource(value = "classpath:META-INF/spring/camel-cxf.xml")
public class RestRoutBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		getContext().getProperties().put("CamelJacksonEnableTypeConverter", "true");
		getContext().getProperties().put("CamelJacksonTypeConverterToPojo", "true");

		from("cxfrs://http://localhost:9090?resourceClasses=com.pgs.service.RestOrderService&bindingStyle=SimpleConsumer")
				.log("BODY BEFORE PROCESSING = ${body}").
				// from("cxfrs:bean:" + ApplicationConfig.REST_BEAN_ID +
				// "?&bindingStyle=SimpleConsumer").log("BODY BEFORE PROCESSING = ${body}").

				process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {
						OrderInquiryType request = exchange.getIn().getBody(OrderInquiryType.class);
						exchange.getOut().setBody(request);
					}

				}).log("BODY AFTER PROCESSING = ${body}").inOut("cxf:bean:orders").marshal().json(JsonLibrary.Jackson, true);
	}
}

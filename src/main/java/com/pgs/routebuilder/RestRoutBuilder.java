package com.pgs.routebuilder;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@Component("restRoutBuilder")
@ImportResource(value = "classpath:META-INF/spring/camel-cxf.xml")
public class RestRoutBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		/* these properties are needed whn we use 2.17 camel version */
		// getContext().getProperties().put("CamelJacksonEnableTypeConverter", "true");
		// getContext().getProperties().put("CamelJacksonTypeConverterToPojo", "true");

		/* more compex way to convert rest to soap and vice versa */
		// from("cxfrs:bean:restService" + "?bindingStyle=SimpleConsumer").
		//
		// process(new Processor() {
		//
		// @Override
		// public void process(Exchange exchange) throws Exception {
		// OrderInquiryType request = exchange.getIn().getBody(OrderInquiryType.class);
		// exchange.getOut().setBody(request);
		// }
		//
		// }).log("BODY AFTER PROCESSING = ${body}").inOut("cxf:bean:orders").process(new Processor() {
		//
		// @Override
		// public void process(final Exchange exchange) throws Exception {
		// OrderInquiryResponseType response = exchange.getIn().getBody(OrderInquiryResponseType.class);
		// exchange.getOut().setBody(response);
		// }
		//
		// });

		/* A new much simpler way to convert rest to soap and vice versa */
		from("cxfrs:bean:restService" + "?bindingStyle=SimpleConsumer").log("After RESTful before SOAP ${body}")
				.setHeader(CxfConstants.OPERATION_NAME, constant("ProcessOrderPlacement"))
				.setHeader(CxfConstants.OPERATION_NAMESPACE, constant("http://www.pgs.com/service/Orders/")).log("In SOAP route only ${body}")
				.to("cxf:bean:orders").marshal().json(JsonLibrary.Jackson, true);

		// Unmarshaling to xml should be investigated
		// JaxbDataFormat jaxb = new JaxbDataFormat("com.pgs.schema.order");
		// from("cxfrs:bean:restService" + "?bindingStyle=SimpleConsumer").setHeader(CxfConstants.OPERATION_NAME,
		// constant("ProcessOrderPlacement"))
		// .setHeader(CxfConstants.OPERATION_NAMESPACE, constant("http://www.pgs.com/service/Orders/")).log("In SOAP route only ${body}")
		// .to("cxf:bean:orders").unmarshal(jaxb);

	}
}

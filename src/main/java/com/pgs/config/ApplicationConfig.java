package com.pgs.config;

import java.util.Arrays;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

//
import com.pgs.service.RestOrderService;

@Configuration
public class ApplicationConfig extends CamelConfiguration {

	public static final String DATA_FORMAT_JACKSON_CXF_PROVIDER_BEAN_ID = "jackson-cxf-provider";
	public final static String REST_BEAN_ID = "rsServer";

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	// @Bean(destroyMethod = "shutdown")
	// public SpringBus cxf() {
	// return new SpringBus();
	// }

	@Bean(name = DATA_FORMAT_JACKSON_CXF_PROVIDER_BEAN_ID)
	public JacksonJsonProvider jsonProvider() {
		return new JacksonJsonProvider();
	}

	@Bean(name = REST_BEAN_ID)
	// @DependsOn("cxf")
	public JAXRSServerFactoryBean rsServer(@Qualifier(DATA_FORMAT_JACKSON_CXF_PROVIDER_BEAN_ID) JacksonJsonProvider jacksonProvider, RestOrderService service
	// ,SpringBus cxf
	) {
		JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();

		// factory.setBus(cxf);
		factory.setServiceBean(service);
		factory.setAddress("http://localhost:9090/");
		factory.setProviders(Arrays.<Object> asList(jacksonProvider));

		return factory;
	}

}

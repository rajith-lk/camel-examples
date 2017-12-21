package ca.randoli.examples.camel.jms.springboot;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteFactory {

	@Autowired
	CamelContext camelContext;
	
	@Autowired
	AppConfiguration appConfig;
	
	@PostConstruct
	public void createRoutes() throws Exception {
		
		for (AppConfiguration.RequestBucket bucket: appConfig.getRequestBuckets()) {
			camelContext.addRoutes(new RequestHandlerRouteBuilder("route-" + bucket.endpoint, bucket.endpoint, bucket.consumers));
		}
	}
}

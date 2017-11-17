package ca.randoli.examples.camel.jms.springboot;

import org.apache.camel.builder.RouteBuilder;

public class RequestHandlerRouteBuilder extends RouteBuilder {

	private String id;
	private String endpointUrl;
	
	public RequestHandlerRouteBuilder(String id, String endpoint, int consumers){
		this.id = id;
		this.endpointUrl = new StringBuilder(endpoint).append("?concurrentConsumers=").append(consumers).toString();
	}
	
	@Override
	public void configure() throws Exception {
	   from(endpointUrl)
	     .routeId(id)
	     .to("log:ca.randoli.examples.camel.springboot?level=INFO&groupInterval=60000&groupActiveOnly=false")
	     //.log("Received request : " + body())
         .setBody(constant("response : " + System.currentTimeMillis()));
         //.log(LoggingLevel.DEBUG, "Computing response : " + body());
	}

}

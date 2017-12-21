package ca.randoli.examples.camel.demo.springboot;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

@Component
public class ApiRouteBuilder extends RouteBuilder { 
	
	public ApiRouteBuilder(){		
	}
	
	@Override
	public void configure() throws Exception {

       restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "Transaction API").apiProperty("api.version", "1.0.0")
            .apiProperty("cors", "true");
        

       rest("/transaction").description("Transaction REST service")
           .consumes("application/json")
           .produces("application/json")

       .get("/{id}").description("Find transaction by ID")
           .outType(ProcessedTransaction.class)
           .param().name("id").type(path).description("The ID of the transaction").dataType("string").endParam()
           .to("direct:retrieveTransaction")    
           
       .post().description("Post a new transaction").type(Transaction.class).outType(ProcessedTransaction.class)
           .param().name("body").type(body).description("The transaction to update").endParam()
           .to("direct:processTransaction");           
	}

}

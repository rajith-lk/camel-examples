package ca.randoli.examples.camel.demo.springboot;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.bean.validator.BeanValidationException;

@ContextName("Demo")
public class TransactionHandlerRouteBuilder extends RouteBuilder {
	
	public TransactionHandlerRouteBuilder(){		
	}
	
	@Override
	public void configure() throws Exception {
	    
	   from("direct:processTransaction")
	    .routeId(TransactionHandlerRouteBuilder.class.getCanonicalName() + "-processTransaction")
        .onException(UnsupportedCurrencyException.class).handled(true).to("direct:errorHandler").setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400)).maximumRedeliveries(0).end()
        .onException(BeanValidationException.class).handled(true).to("direct:errorHandler").setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400)).maximumRedeliveries(0).end()
        .to("bean-validator://application")
        .to("bean:transactionService?method=transformTransactionFormat")
        .to("bean:currencyService?method=convertCurrency")        
        .setHeader(Exchange.FILE_NAME, simple("${body.id}"))
        .marshal().xstream("ca.randoli.examples.camel.demo.springboot.ProcessedTransaction")
        .to("file:///tmp/camel-demo");
	   
	   from("direct:retrieveTransaction")
	    .routeId(TransactionHandlerRouteBuilder.class.getCanonicalName() + "-retrieveTransaction")
	    .onException(BeanValidationException.class).handled(true).to("direct:errorHandler").setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404)).maximumRedeliveries(0).end() 
        .to("bean:transactionService?method=retrieveTransaction")
        .unmarshal().xstream("ca.randoli.examples.camel.demo.springboot.ProcessedTransaction");
	   
	   
	   from("direct:errorHandler")
	    .routeId(TransactionHandlerRouteBuilder.class.getCanonicalName() + "-errorHandler") 
	    .log(LoggingLevel.INFO, "Exception processing request")
        .process((Exchange exchange) -> {
           Exception ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
           exchange.getIn().setBody(ex.getMessage());
        })        
        .setHeader(Exchange.CONTENT_TYPE, constant("application/text"));
	}

}

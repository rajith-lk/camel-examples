package ca.randoli.examples.camel.demo.springboot;

public class InvalidTransactionId extends Exception{

	public InvalidTransactionId(String msg) {
		super(msg);
	}
	
}

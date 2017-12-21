package ca.randoli.examples.camel.demo.springboot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("currencyService")
public class CurrencyService {

	Map<String, Double> rates = new HashMap<String, Double>(); 
	
	public CurrencyService (){
		rates.put("CAD", 1.0);
		rates.put("USD", 1.2);
		rates.put("EUR", 1.5);
		rates.put("GBP", 1.75);
	}

	public void convertCurrency(ProcessedTransaction tx) throws UnsupportedCurrencyException {

		if(rates.containsKey(tx.getCurrencyCode())) {
			
			tx.setConvertedAmount(tx.getAmount() * rates.get(tx.getCurrencyCode()));
			
		} else {
			throw new UnsupportedCurrencyException(tx.getCurrencyCode() + " is not a supported currency");
		}
	}
}

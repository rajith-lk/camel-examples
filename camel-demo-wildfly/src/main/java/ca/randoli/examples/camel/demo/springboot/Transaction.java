package ca.randoli.examples.camel.demo.springboot;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Transaction {

	@NotNull(message = "Name cannot be null")
	String id;

	@NotNull(message = "Name cannot be null")
	String currencyCode;

	@Min(value = 100, message = "Minimum amount should be 100")
	@Max(value = 100000, message = "Amount should not exceed 100,000")
	double amount;

	Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
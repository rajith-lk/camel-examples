package ca.randoli.examples.camel.demo.springboot;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.camel.Header;
import org.springframework.stereotype.Component;

@Component("transactionService")
public class TransactionService {

	public ProcessedTransaction transformTransactionFormat(Transaction tx) {

		ProcessedTransaction processedTx = new ProcessedTransaction();
		processedTx.setId(tx.getId());
		processedTx.setAmount(tx.getAmount());
		processedTx.setCurrencyCode(tx.getCurrencyCode());
		processedTx.setDate(tx.getDate());
		processedTx.setProcessedDate(new Date());

		return processedTx;
	}
	
	public String retrieveTransaction(@Header("id") String id) throws Exception{
		try {
			return new String(Files.readAllBytes(Paths.get("/tmp/camel-demo/" + id)));
		} catch (FileNotFoundException e) {
			throw new InvalidTransactionId(id + " is not a valid transaction id");
		}
	}
}

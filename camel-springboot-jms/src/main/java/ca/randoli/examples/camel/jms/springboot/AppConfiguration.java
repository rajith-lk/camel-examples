package ca.randoli.examples.camel.jms.springboot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class AppConfiguration {

	List<RequestBucket> requestBuckets = new ArrayList<RequestBucket>();

	String brokerUrl;

	public List<RequestBucket> getRequestBuckets() {
		return requestBuckets;
	}

	public void setRequestBuckets(List<RequestBucket> requestBuckets) {
		this.requestBuckets = requestBuckets;
	}

	public String getBrokerUrl() {
		return brokerUrl;
	}

	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	public static class RequestBucket {

		String endpoint;
		int consumers;

		public String getEndpoint() {
			return endpoint;
		}

		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}

		public int getConsumers() {
			return consumers;
		}

		public void setConsumers(int consumerCount) {
			this.consumers = consumerCount;
		}
	}
}
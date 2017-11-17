package ca.randoli.examples.camel.jms.springboot;

import java.lang.management.ManagementFactory;

import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class MessagingComponentFactory {

	private final Logger log = LoggerFactory.getLogger(MessagingComponentFactory.class);

	@Autowired
	AppConfiguration appConfig;

	/**
	 * Used by consumers consuming from endpoints with high volume, to void sharing
	 * a connection with consumers on other endpoints
	 * 
	 * @return
	 */

	@Bean
	AMQPComponent amqpHT() {

		String brokerURL = appConfig.getBrokerUrl();

		log.info("======================");
		log.info("Connecting to brokers : " + brokerURL);
		log.info("======================");

		JmsConnectionFactory connectionFactory = new JmsConnectionFactory(brokerURL);
		CachingConnectionFactory cacheFactory = new CachingConnectionFactory();
		cacheFactory.setTargetConnectionFactory(connectionFactory);

		JmsConfiguration jmsConfig = new JmsConfiguration();
		jmsConfig.setConnectionFactory(connectionFactory);
		jmsConfig.setClientId("CamelApp-" + ManagementFactory.getRuntimeMXBean().getName());

		AMQPComponent amqp = new AMQPComponent();
		amqp.setConfiguration(jmsConfig);

		return amqp;
	}

	/**
	 * Used by consumers consuming from endpoints with very little volume.
	 * 
	 * @return
	 */
	@Bean
	AMQPComponent amqpLT() {

		String brokerURL = appConfig.getBrokerUrl();

		log.info("======================");
		log.info("Connecting to brokers : " + brokerURL);
		log.info("======================");

		JmsConnectionFactory connectionFactory = new JmsConnectionFactory(brokerURL);
		CachingConnectionFactory cacheFactory = new CachingConnectionFactory();
		cacheFactory.setTargetConnectionFactory(connectionFactory);

		JmsConfiguration jmsConfig = new JmsConfiguration();
		jmsConfig.setConnectionFactory(cacheFactory);
		jmsConfig.setClientId("CamelApp-" + ManagementFactory.getRuntimeMXBean().getName());

		AMQPComponent amqp = new AMQPComponent();
		amqp.setConfiguration(jmsConfig);

		return amqp;
	}

}
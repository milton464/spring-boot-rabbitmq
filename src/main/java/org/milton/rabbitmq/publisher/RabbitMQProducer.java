package org.milton.rabbitmq.publisher;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author MILTON
 */
@Slf4j
@Service
public class RabbitMQProducer {

	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.key}")
	private String routingKey;
	
	@Value("${rabbitmq.delayed.exchange}")
	private String delayedExchange;
	
	
	private RabbitTemplate rabbitTemplate;
	
	public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	
	public void sendMessage(String message) {
		log.info("message: {}", message+ "publishtime: "+LocalDateTime.now());
		rabbitTemplate.convertAndSend(exchange, routingKey, message);
	}
	
	
	public void sendDelayedMessage(String message, long delayMillis) {
		   log.info("Sending delayed message: {}", message + "publicTime: " + LocalDateTime.now());
	       rabbitTemplate.convertAndSend(delayedExchange, routingKey, message, me -> {
	           me.getMessageProperties().setDelay((int) delayMillis);	
	           return me;
	       });
	   }
}


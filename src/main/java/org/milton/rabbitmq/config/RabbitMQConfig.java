package org.milton.rabbitmq.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MILTON
 */
@Configuration
public class RabbitMQConfig {


	@Value("${rabbitmq.queue.name}")
	private String queue;
	
	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.key}")
	private String routingKey;

	
	@Value("${rabbitmq.delayed.queue}")
	private String delayedQueue;
	
	@Value("${rabbitmq.delayed.exchange}")
	private String delayedExchange;
	
	
	@Bean
	public Queue queue() {
		return new Queue(queue);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchange);
	}
	
	@Bean
	public Binding binding() {
		return BindingBuilder
				.bind(queue())
				.to(exchange())
				.with(routingKey);
	}

	@Bean
	public Queue delayedQueue() {
		return new Queue(delayedQueue);
	}
	
	@Bean
	public CustomExchange customExchange() {
		 Map<String, Object> args = new HashMap<>();
		    args.put("x-delayed-type", "direct");
		return new CustomExchange(delayedExchange, "x-delayed-message", true, false, args);
	}
	
	@Bean
	public Binding delayedBinding() {
		return BindingBuilder.bind(delayedQueue()).to(customExchange()).with(routingKey).noargs();
	}
	

}

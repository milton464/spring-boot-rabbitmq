package org.milton.rabbitmq.consumer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author MILTON
 */
@Service
@Slf4j
public class RabbitMQConsumer {

	@RabbitListener(queues = { "${rabbitmq.queue.name}" })
	public void consumeMessage(String message) {
		log.info("message consumed successFully: {}", message +" consumeTime: "+LocalDateTime.now());
	}

	@RabbitListener(queues = { "${rabbitmq.delayed.queue}" })
	public void consumeDelayedMessage(String message) {
		
		log.info("delayed message consumed successFully: {}", message+ " consume time:"+LocalDateTime.now());
	}
	
}

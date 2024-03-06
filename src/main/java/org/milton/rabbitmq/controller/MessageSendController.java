package org.milton.rabbitmq.controller;

import org.milton.rabbitmq.dto.Request;
import org.milton.rabbitmq.publisher.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author MILTON
 */
@RestController
@RequestMapping("api/v1")
@Slf4j
public class MessageSendController {

	private static final String RABBITMQ_API_URL = "http://localhost:15672/api";
	
	private final String RABBITMQ_EXCHANGE_API_URL = "http://localhost:15672/api/exchanges";
													  
	
	@Autowired
	private RabbitMQProducer producer;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/publish")
	public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
		producer.sendMessage(message);
		return new ResponseEntity<>("Message send successfully: " + message, HttpStatus.OK);
	}

	@PostMapping("/publish/delayed")
	public ResponseEntity<String> sendDelayedMessage(@RequestBody Request requestBody) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		String message = "";
		try {
			message = objectMapper.writeValueAsString(requestBody);
		} catch (Exception e) {
			log.error("ERROR: {}", e);
		}
		
		producer.sendDelayedMessage(message, 1800000); // 1800000
		return new ResponseEntity<>("Message send successfully: " + message, HttpStatus.OK);
	}
	
	@GetMapping("/queues")
	public ResponseEntity<String> getQueues() {
		
		String username = "guest";
		String password = "guest";
		
		String url = RABBITMQ_API_URL + "/queues";
		String authHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authHeaderValue);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();
		
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}

	@GetMapping("/exchange")
	public ResponseEntity<String> getExchange() {
		
		String username = "guest";
		String password = "guest";
		
		String url = RABBITMQ_EXCHANGE_API_URL;
		String authHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authHeaderValue);
		
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		String responseBody = response.getBody();
		
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	
}

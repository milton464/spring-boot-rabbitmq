package org.milton.rabbitmq.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author MILTON
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Request {

	private BigInteger id;
	
	private String message;
	
	private String uid;
	
}

package com.microservice.apigateway.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class ApiFallback {

	@GetMapping("/fallback")
	public Mono<String> fallback(ServerWebExchange exchange) {
		Throwable throwable = exchange.getAttribute(ServerWebExchange.LOG_ID_ATTRIBUTE);
		return Mono.just("Fallback: Service is down. Please try again later. Error: "
				+ (throwable != null ? throwable.getMessage() : "Unknown"));
	}
}
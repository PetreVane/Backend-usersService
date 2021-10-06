package com.orbsec.photobackendusersapi;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@SpringBootApplication
public class PhotoBackendUsersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoBackendUsersApiApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate () {
		return new RestTemplate();
	}

	@Bean
	@Profile("!production")
	Logger.Level feignLogger() {
		return Logger.Level.FULL;
	}

}

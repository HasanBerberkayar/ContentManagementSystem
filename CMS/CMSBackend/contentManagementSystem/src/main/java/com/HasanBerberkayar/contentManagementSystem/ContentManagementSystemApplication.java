package com.HasanBerberkayar.contentManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ContentManagementSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(ContentManagementSystemApplication.class, args);
	}

}

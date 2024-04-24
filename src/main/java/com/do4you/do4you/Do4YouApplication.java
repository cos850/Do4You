package com.do4you.do4you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class Do4YouApplication {

	public static void main(String[] args) {
		SpringApplication.run(Do4YouApplication.class, args);
	}

}

package com.edigest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

	// For @Transactional implementation
	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

// PlatformTransactionManager - Interface for managing rollback and commit

// MongoTransactionManager - PTM is implemented from this interface

// MongoDatabaseFactory - helps to establish connection with DB for performing
// commits and rollbacks
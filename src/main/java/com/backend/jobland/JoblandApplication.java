package com.backend.jobland;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class JoblandApplication {

	private static final Logger log = LoggerFactory.getLogger(JoblandApplication.class);

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(JoblandApplication.class, args);

		log.info("Jobland Backend started successfully on port: " + System.getProperty("PORT"));
	}

}

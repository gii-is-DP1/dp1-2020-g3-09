package com.tempura17;

import com.tempura17.web.LoggingController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Tempura17Application {

	private static final Logger log = LoggerFactory.getLogger(Tempura17Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Tempura17Application.class, args);
		log.info("Awesomeness ahead!");

	}

}

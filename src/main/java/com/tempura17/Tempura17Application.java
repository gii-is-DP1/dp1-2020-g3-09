package com.tempura17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Tempura17Application {

	public static void main(String[] args) {
		SpringApplication.run(Tempura17Application.class, args);
		log.info("Awesomeness ahead!");

	}

}

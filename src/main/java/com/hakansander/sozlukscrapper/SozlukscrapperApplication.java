package com.hakansander.sozlukscrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SozlukscrapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SozlukscrapperApplication.class, args);
	}

}

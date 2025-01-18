package com.conexa.swapi_integration;

import com.conexa.swapi_integration.service.PeopleService;
import com.conexa.swapi_integration.service.impl.PeopleServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SwapiIntegrationApplication {

	public static void main(String[] args) {
		PeopleService peopleService = new PeopleServiceImpl();
            peopleService.findPeopleById(1);
        SpringApplication.run(SwapiIntegrationApplication.class, args);


	}

}

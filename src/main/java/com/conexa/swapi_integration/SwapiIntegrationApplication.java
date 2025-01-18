package com.conexa.swapi_integration;

import com.conexa.swapi_integration.service.FilmService;
import com.conexa.swapi_integration.service.PeopleService;
import com.conexa.swapi_integration.service.impl.FilmServiceImpl;
import com.conexa.swapi_integration.service.impl.PeopleServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SwapiIntegrationApplication {

	public static void main(String[] args) {
		FilmService filmService = new FilmServiceImpl();
		PeopleService peopleService = new PeopleServiceImpl();
			peopleService.getAllPeople(1,5);
            filmService.getAllFilms(1,5);

            //peopleService.findPeopleById(2);
        SpringApplication.run(SwapiIntegrationApplication.class, args);


	}

}

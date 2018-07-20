package com.netent.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by nayan.kakati on 11/20/17.
 */

@SpringBootApplication
@ComponentScan("com.netent.*")
public class GameApplication extends SpringBootServletInitializer {

	private static final Class<GameApplication> gameApplicationClass = GameApplication.class;
	private static final Logger log = LoggerFactory.getLogger(gameApplicationClass);

	public static void main(String[] args) {
		SpringApplication.run(gameApplicationClass, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(gameApplicationClass);
	}
}

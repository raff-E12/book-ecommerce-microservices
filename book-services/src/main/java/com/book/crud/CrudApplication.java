package com.book.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// Dove Scansionare i diversi: bean, repositery e le diverse entity
@ComponentScan(basePackages = { "com.book.crud", "com.book.crud.controller", "com.book.crud.services", "com.book.crud.mapper" })
@EnableJpaRepositories(basePackages = { "com.book.crud.repository" })
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

}

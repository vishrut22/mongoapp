package com.mongo.demo.mongoapp;

import com.mongo.demo.mongoapp.collection.Address;
import com.mongo.demo.mongoapp.collection.Person;
import com.mongo.demo.mongoapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@SpringBootApplication
@EnableSwagger2
//@EnableMongoRepositories // Just in case if want to explain
public class MongoappApplication {

	public static void main(String[] args) {

		SpringApplication.run(MongoappApplication.class, args);
	}



}

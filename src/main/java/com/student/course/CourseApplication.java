package com.student.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = "com.student.model")
@EnableJpaRepositories(basePackages = "com.student.repository")
@ComponentScan(basePackages = {"com.student.controller", "com.student.service", "com.student.repository"})
public class CourseApplication {


	public static void main(String[] args) {
		SpringApplication.run(CourseApplication.class, args);
	}


}
package com.scheduler;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@EntityScan(basePackages = "com.scheduler.model", basePackageClasses = {
		com.scheduler.model.EmployeeDetails.class, com.scheduler.model.Scheduler.class })
@EnableAutoConfiguration
@SpringBootApplication
public class SchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}

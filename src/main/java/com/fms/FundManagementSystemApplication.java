package com.fms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableJpaRepositories(basePackages = {"com.fms.repositories"})
@EntityScan({"com.fms.entities"})
@ComponentScan({"com.fms"})
@SpringBootApplication
@Slf4j
public class FundManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundManagementSystemApplication.class, args);
	}


}

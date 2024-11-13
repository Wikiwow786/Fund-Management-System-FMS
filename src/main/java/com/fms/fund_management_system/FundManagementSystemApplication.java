package com.fms.fund_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableJpaRepositories(basePackages = {"com.fms.fund_management_system.repositories"})
@EntityScan({"com.fms.fund_management_system.entities"})
@ComponentScan({"com.fms.fund_management_system"})
@SpringBootApplication
public class FundManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundManagementSystemApplication.class, args);
	}

}

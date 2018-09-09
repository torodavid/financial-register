package com.torodavid.thesis.financialregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class FinancialRegisterApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FinancialRegisterApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(FinancialRegisterApplication.class, args);
	}
}

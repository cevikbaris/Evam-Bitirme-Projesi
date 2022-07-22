package com.evam.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class BillPaymentProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillPaymentProjectApplication.class, args);
	}

}

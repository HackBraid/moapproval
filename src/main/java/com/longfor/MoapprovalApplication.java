package com.longfor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@Configuration
@SpringBootApplication
public class MoapprovalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoapprovalApplication.class, args);
	}
}

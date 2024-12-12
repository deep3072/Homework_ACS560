package com.example.hw13_aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Hw13AopApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hw13AopApplication.class, args);
	}

}

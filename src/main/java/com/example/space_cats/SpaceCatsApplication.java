package com.example.space_cats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpaceCatsApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpaceCatsApplication.class, args);
	}

}

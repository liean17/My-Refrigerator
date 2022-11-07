package com.toy.refrigerator;

import com.toy.refrigerator.config.JpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(JpaConfig.class)
@SpringBootApplication
public class RefrigeratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefrigeratorApplication.class, args);
	}

}

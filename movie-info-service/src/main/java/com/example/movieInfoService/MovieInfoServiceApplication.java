package com.example.movieInfoService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@OpenAPIDefinition(info =
@Info(title = "Movie API", version = "1.0", description = "Documentation Movie API v1.0")
)
@EnableDiscoveryClient
public class MovieInfoServiceApplication {


	public static void main(String[] args) {

		SpringApplication.run(MovieInfoServiceApplication.class, args);
	}
}

package com.example.apiGateway;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URI;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableSwagger2
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {

		return builder.routes()
				.route("movie-service", p -> p
						.path("/api/movie/**")
						.filters(f -> f.circuitBreaker(x -> x.setFallbackUri("forward:/movieFallback")
								.setName("movie-service"))
								.rewritePath("/api/movie/v3/api-docs", "/v3/api-docs/")
								)
						.uri("lb://movie-service"))

				.route("movie-catelog-service", p -> p
						.path("/api/catelog/**")
						.filters(f -> f.circuitBreaker(x -> x.setFallbackUri("forward:/movieCatalogFallback")
										.setName("movie-catelog-service"))
								.rewritePath("/api/catelog/v3/api-docs", "/v3/api-docs/")
						)
						.uri("lb://movie-catelog-service"))

				.route("rating-data-service", p -> p
				.path("/api/ratingsdata/**")
				.filters(f -> f.circuitBreaker(x -> x.setFallbackUri("forward:/ratingsdataFallback")
								.setName("rating-data-service"))
						.rewritePath("/api/ratingsdata/v3/api-docs", "/v3/api-docs/")
				)
				.uri("lb://rating-data-service"))
				.build();
	}
}

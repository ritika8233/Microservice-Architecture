package com.example.apiGateway.Controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@ApiIgnore
public class FallbackController {

    @Autowired
    private RouteLocator routeLocator;

    @RequestMapping("/movieFallback")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
    })
    public Mono<String> movieServiceFallback(){
        return Mono.just("Movie Service is taking too long to respond or is down. Please try again later.");
    }

    @RequestMapping("/ratingsdataFallback")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
    })
    public Mono<String> ratingsDataServiceFallback(){
        return Mono.just("Ratings Data Service is taking too long to respond or is down. Please try again later.");
    }

    @RequestMapping("/movieCatalogFallback")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
    })
    public Mono<String> movieCatalogServiceFallback(){
        return Mono.just("Movie Catalog Service is taking too long to respond or is down. Please try again later.");
    }
}

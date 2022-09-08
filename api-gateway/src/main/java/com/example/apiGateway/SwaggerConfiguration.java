package com.example.apiGateway;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements SwaggerResourcesProvider {

    @Autowired private RouteLocator routeLocator;
//    @Autowired private RouteDefinitionLocator routeDefinitionLocator;

    public static final String API_URI = "/v3/api-docs";

    @Bean
    public Docket orderApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.any())
                .build();
               // .apiInfo(getApiInfo());
    }


    @Bean
    public List<GroupedOpenApi> api(){

        List<GroupedOpenApi> groups = new ArrayList<>();

        List<Route> routes = routeLocator.getRoutes().toStream().collect(Collectors.toList());
        System.out.println("list ritu:" + routes.size());

        routes.stream().filter(route -> route.getId().matches(".*-service")).forEach(route -> {
            String name = route.getId().replaceAll("-service", "");
            GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();

        });

        return groups;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        routeLocator.getRoutes().subscribe(routeDefinition -> {
            //log.info("Discovered route definition: {}", routeDefinition.getId());
            String resourceName = routeDefinition.getId();
            String location = routeDefinition.getPredicate().toString().split(",")[0];
            location = location.substring(8, location.length()-1);
            location = location.replace("/**", API_URI);

            System.out.println("loc: " + location);
           // log.info("Adding swagger resource: {} with location {}", resourceName, location);
            resources.add(swaggerResource(resourceName, location));
        });

        return resources;
    }


    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

//    public List<GroupedOpenApi> groupedOpenApis(){
//        List<GroupedOpenApi> groups = new ArrayList<>();
//
//        List<RouteDefinition> definitions = routeDefinitionLocator.getRouteDefinitions().collectList().block();
//        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")).forEach(routeDefinition -> {
//            String name = routeDefinition.getId().replaceAll("-service", "");
//            GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
//                    //setGroup(name).build();
//        });
//        return groups;
//    }


//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> list = new ArrayList<>();
//        routeLocator.getRoutes().forEach(route -> {
//                            list.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs"), "1.0"));
//                            //System.out.println("Retro" + route.getPredicate().toString().split(",")[0] + "--->");
//                        }
//                );
//
//        return list;
//    }
//
//    private SwaggerResource swaggerResource(final String name, final String location, final String version) {
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(name);
//        swaggerResource.setLocation(location);
//        swaggerResource.setSwaggerVersion(version);
//        return swaggerResource;
//    }

}

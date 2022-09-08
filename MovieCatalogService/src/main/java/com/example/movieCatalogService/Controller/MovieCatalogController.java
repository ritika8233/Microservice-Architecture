package com.example.movieCatalogService.Controller;

import com.example.movieCatalogService.Model.CatalogItem;
import com.example.movieCatalogService.Model.Movie;
import com.example.movieCatalogService.Model.Rating;
import com.example.movieCatalogService.Model.UserRating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/catelog")
@RefreshScope
public class MovieCatalogController {

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${microservice.movie-service.endpoints.endpoint.uri}")
    public String movieService;

    @Value("${microservice.rating-data-service.endpoints.endpoint.uri}")
    public String ratingService;


    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable Long userId) {

        log.info("Movie-Catalog");
        System.out.println("movieService: " + movieService + " rating :" + ratingService);

       // UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, UserRating.class);
        //UserRating ratings = restTemplate.getForObject("http://rating-data-service/api/ratingsdata/user/" + userId, UserRating.class);
        UserRating ratings = restTemplate.getForObject(ratingService + userId, UserRating.class);

        return ratings.getRatings().stream().map(rating -> {
                   // Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
                    //Movie movie = restTemplate.getForObject("http://movie-service/api/movie/" + rating.getMovieId(), Movie.class);
                    Movie movie = restTemplate.getForObject(movieService + rating.getMovieId(), Movie.class);

            /*Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
                */
                    return new CatalogItem(movie.getName(), "Test", rating.getRating());
                })
                .collect(Collectors.toList());
    }

}

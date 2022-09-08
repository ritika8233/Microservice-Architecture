package com.example.ratingsDataService.Controller;

import com.example.ratingsDataService.Model.Rating;
import com.example.ratingsDataService.Model.UserRating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ratingsdata")
public class RatingController {

    @RequestMapping("/{movieId}")
    public Rating getMovieRating(@PathVariable Long movieId){
        log.info("Rating Hit");
        return new Rating(movieId, 5L);
    }

    @RequestMapping("/user/{userId}")
    public UserRating getRating(@PathVariable Long userId) {
        log.info("Rating Hit");
        List<Rating> ratings = Arrays.asList(
                new Rating(123L, 4L),
                new Rating(234L, 1L)
        );

        UserRating userRating = new UserRating();
        userRating.setRatings(ratings);

        return userRating;
    }
}

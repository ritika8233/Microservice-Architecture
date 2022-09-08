package com.example.movieInfoService.Controller;

import com.example.movieInfoService.Model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/movie")
public class MovieInfoController {
// Already up
    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable Long movieId){
        log.info("API Hit");
        return new Movie(movieId, "Harry Potter");
    }

}

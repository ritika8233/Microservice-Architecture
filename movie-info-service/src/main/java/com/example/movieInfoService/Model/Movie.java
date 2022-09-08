package com.example.movieInfoService.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {
    private Long movieId;
    private String name;
}

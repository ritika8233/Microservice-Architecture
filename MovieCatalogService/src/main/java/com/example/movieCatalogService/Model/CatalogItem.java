package com.example.movieCatalogService.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CatalogItem {

    private String name;
    private String desc;
    private Long rating;
}

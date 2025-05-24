package com.fastmarket.fastmarket_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreerListeCoursesRequest {
    private Long clientId;
    private String nom;
}
package com.fastmarket.fastmarket_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AjouterPostItListeRequest {
    private Long listeId;
    private String contenu;
}

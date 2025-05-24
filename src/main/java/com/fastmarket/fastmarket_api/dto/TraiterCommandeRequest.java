package com.fastmarket.fastmarket_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraiterCommandeRequest {
    private Long commandeId;
    private Long preparateurId;
}
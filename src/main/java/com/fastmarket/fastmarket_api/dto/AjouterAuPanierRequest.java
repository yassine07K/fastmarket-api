package com.fastmarket.fastmarket_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AjouterAuPanierRequest {
    private Long clientId;
    private Long produitId;
    private Integer quantite;
}
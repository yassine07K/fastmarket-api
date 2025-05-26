package com.fastmarket.fastmarket_api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ValiderPanierRequest {
    private Long clientId;
    private Long magasinId;
    private Long creneauId;

}

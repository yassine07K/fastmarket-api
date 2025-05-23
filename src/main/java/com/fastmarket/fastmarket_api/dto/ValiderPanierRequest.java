package com.fastmarket.fastmarket_api.dto;

import com.fastmarket.fastmarket_api.model.Produit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValiderPanierRequest {
    private Long clientId;
    private Long magasinId;
    private Long creneauId;

}

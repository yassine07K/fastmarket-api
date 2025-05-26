package com.fastmarket.fastmarket_api.dto;


import com.fastmarket.fastmarket_api.model.PostIt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListeCoursesDetails {
    private Long id;
    private String nom;
    private List<ProduitInListe> produits;
    private List<PostIt> postIts;
}

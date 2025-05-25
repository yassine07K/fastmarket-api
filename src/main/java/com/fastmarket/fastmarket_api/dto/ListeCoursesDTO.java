package com.fastmarket.fastmarket_api.dto;

import java.util.List;

public class ListeCoursesDTO {
    private Long id;
    private String nom;
    private Long clientId;
    private List<ProduitInListe> produits;

    // Constructeurs, getters et setters

    public ListeCoursesDTO() {}

    public ListeCoursesDTO(Long id, String nom, Long clientId, List<ProduitInListe> produits) {
        this.id = id;
        this.nom = nom;
        this.clientId = clientId;
        this.produits = produits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<ProduitInListe> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitInListe> produits) {
        this.produits = produits;
    }
}
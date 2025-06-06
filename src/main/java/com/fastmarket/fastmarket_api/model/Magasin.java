package com.fastmarket.fastmarket_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Magasin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Magasin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "adresse")
    private String adresse;

    @ManyToOne
    @JoinColumn(name = "Gerant_id")
    private Gerant gerant;

    @ManyToMany
    @JoinTable(
            name = "creneau_mag",
            joinColumns = @JoinColumn(name = "magasin_id"),
            inverseJoinColumns = @JoinColumn(name = "creneau_id")
    )
    private List<Creneau> creneaux;

    @OneToMany(mappedBy = "magasin")
    @JsonIgnore
    private List<Produit> produits;

}

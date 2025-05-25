package com.fastmarket.fastmarket_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "Liste_Courses_Produit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListeCoursesProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "liste_id")
    private ListeCourses liste;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

}
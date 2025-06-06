package com.fastmarket.fastmarket_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Liste_Courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListeCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nom")
    private String nom;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "liste", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListeCoursesProduit> produits;

    @ManyToMany
    @JoinTable(
            name = "Liste_Courses_Post_It",
            joinColumns = @JoinColumn(name = "liste_id"),
            inverseJoinColumns = @JoinColumn(name = "post_it_id")
    )
    private List<PostIt> postIts;


}

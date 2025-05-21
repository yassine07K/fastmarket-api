package com.fastmarket.fastmarket_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "utilisateurs") // doit correspondre exactement au nom de ta vue
@Getter
@Setter
public class Utilisateur {

    @Id
    @Column(name = "email")
    private String email; // email est unique, donc on peut l'utiliser comme clé

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "role")
    private String role;
}

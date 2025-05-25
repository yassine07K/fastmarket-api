package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.ListeCoursesProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListeCoursesProduitRepository extends JpaRepository<ListeCoursesProduit, Long> {
    List<ListeCoursesProduit> findByListe_Id(Long listeId);
}

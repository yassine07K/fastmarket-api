package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.ListeCoursesPostIt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListeCoursesPostItRepository extends JpaRepository<ListeCoursesPostIt, Long> {
    List<ListeCoursesPostIt> findByListe_Id(Long listeId);
    void deleteByListeIdAndPostItId(Long listeId,Long postItId);
}
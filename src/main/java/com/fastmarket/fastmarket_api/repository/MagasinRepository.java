package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.Magasin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MagasinRepository extends JpaRepository<Magasin, Long> {
    List<Magasin> findTop5ByOrderByIdAsc();

    Optional<Object> findByGerant_Id(Long gerantId);
}

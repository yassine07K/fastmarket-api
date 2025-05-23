package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.Magasin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MagasinRepository extends JpaRepository<Magasin, Long> {
    List<Magasin> findTop5ByOrderByIdAsc();
}

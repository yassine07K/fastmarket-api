package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.PostIt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostItRepository extends JpaRepository<PostIt, Long> {
}
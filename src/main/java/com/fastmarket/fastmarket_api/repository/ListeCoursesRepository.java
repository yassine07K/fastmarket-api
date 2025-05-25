package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.ListeCourses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListeCoursesRepository extends JpaRepository<ListeCourses, Long> {
    List<ListeCourses> findByClient_Id(Long clientId);
}


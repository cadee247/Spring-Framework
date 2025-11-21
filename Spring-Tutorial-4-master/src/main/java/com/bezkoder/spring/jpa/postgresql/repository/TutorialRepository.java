package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.model.Tutorial;

// Repository interface for Tutorial entity
// Extends JpaRepository to inherit CRUD operations and query support
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

    // Custom query method to find tutorials by published status (true/false)
    List<Tutorial> findByPublished(boolean published);

    // Custom query method to search tutorials by title (partial match)
    List<Tutorial> findByTitleContaining(String title);
}

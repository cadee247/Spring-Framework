package org.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.project.model.Article; // ← Ensure this points to the correct Article class
import java.util.List;

/**
 * Repository interface for managing Article entities.
 *
 * Extends Spring Data JPA's JpaRepository to inherit common CRUD operations:
 *  - save(), findById(), findAll(), deleteById(), etc.
 *
 * Custom query methods are also defined here using Spring Data's query derivation.
 * Spring will automatically generate the SQL based on method names.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * Finds all articles whose title contains the given keyword.
     *
     * Spring Data JPA interprets the method name and generates a query like:
     *   SELECT * FROM articles WHERE title LIKE %:title%
     *
     * @param title substring to search for in article titles
     * @return list of articles with titles containing the keyword
     */
    List<Article> findByTitleContaining(String title);

    /**
     * Finds all articles filtered by their published status.
     *
     * Spring Data JPA generates a query like:
     *   SELECT * FROM articles WHERE published = :published
     *
     * @param published true = only published articles, false = only drafts
     * @return list of articles matching the published flag
     */
    List<Article> findByPublished(boolean published);
}
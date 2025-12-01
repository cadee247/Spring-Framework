package org.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.project.model.Article;
import org.example.project.repository.ArticleRepository;

import java.util.List;

/**
 * Service layer for managing Article entities.
 *
 * Responsibilities:
 * - Acts as a bridge between the controller and repository.
 * - Encapsulates business logic (currently minimal, but can be expanded).
 * - Delegates persistence operations to ArticleRepository.
 *
 * QA Note:
 * Keeping logic in the service layer ensures controllers remain thin and focused
 * on request/response handling.
 */
@Service
public class ArticleService {

    /**
     * Injected ArticleRepository instance.
     *
     * - @Autowired lets Spring automatically provide the repository bean.
     * - This repository handles all database operations for Article entities.
     */
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Retrieve all articles from the database.
     *
     * @return list of all Article records
     * QA Note: Returns an empty list if no articles exist (never null).
     */
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    /**
     * Find articles whose titles contain the given keyword.
     *
     * @param title substring to search for
     * @return list of matching articles
     * QA Note: Case sensitivity depends on database collation.
     */
    public List<Article> findByTitleContaining(String title) {
        return articleRepository.findByTitleContaining(title);
    }

    /**
     * Find a single article by its ID.
     *
     * @param id unique identifier of the article
     * @return Article if found, otherwise null
     * QA Note: Using orElse(null) avoids Optional handling in controllers.
     */
    public Article findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    /**
     * Save a new article or update an existing one.
     *
     * @param article entity to persist
     * @return saved Article with generated ID (for new records)
     * QA Note: If the ID exists, JPA performs an update; otherwise, an insert.
     */
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    /**
     * Delete an article by its ID.
     *
     * @param id unique identifier of the article
     * QA Note: If the ID does not exist, JPA silently ignores the call.
     */
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    /**
     * Delete all articles from the database.
     *
     * QA Note: Use with caution — this operation clears the entire table.
     */
    public void deleteAll() {
        articleRepository.deleteAll();
    }

    /**
     * Find articles filtered by published status.
     *
     * @param published true = only published, false = only drafts
     * @return list of articles matching the published flag
     * QA Note: Useful for filtering content in client apps.
     */
    public List<Article> findByPublished(boolean published) {
        return articleRepository.findByPublished(published);
    }
}
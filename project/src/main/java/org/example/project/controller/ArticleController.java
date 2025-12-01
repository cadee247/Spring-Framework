package org.example.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.project.model.Article;
import org.example.project.service.ArticleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST Controller for managing Articles.
 * Defines CRUD API endpoints and returns appropriate HTTP responses.
 */
@Tag(name = "Article", description = "Article management APIs")
@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:8080") // Allow frontend hosted on this origin
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * Create a new Article.
     * @param article request body containing article fields.
     * @return ResponseEntity with created Article and HTTP 201.
     */


    @Operation(summary = "Create a new Article")
    @ApiResponse(responseCode = "201", description = "Article created successfully")
    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        System.out.println("DEBUG: Inside POST /api/articles");
        System.out.println("DEBUG: Received Article = " + article);

        try {
            Article saved = articleService.save(article);

            System.out.println("DEBUG: Saved Article = " + saved);

            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("DEBUG: Exception in POST:");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve all articles.
     * Optional: filter by title using query parameter.
     * @param title optional title filter (?title=something)
     * @return list of articles or 204 if empty.
     */
    @Operation(summary = "Get all Articles")
    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles(@RequestParam(required = false) String title) {
        System.out.println("DEBUG: Inside GET /api/articles");
        System.out.println("DEBUG: title filter = " + title);

        List<Article> articles = (title == null)
                ? articleService.findAll()
                : articleService.findByTitleContaining(title);

        System.out.println("DEBUG: Retrieved articles = " + articles);

        return articles.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(articles, HttpStatus.OK);
    }

    /**
     * Find a single article by ID.
     * @param id path variable for article ID.
     * @return Article or 404 if not found.
     */
    @Operation(summary = "Get an Article by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable long id) {
        System.out.println("DEBUG: Inside GET /api/articles/" + id);

        Article article = articleService.findById(id);

        System.out.println("DEBUG: Found article = " + article);

        return (article != null)
                ? new ResponseEntity<>(article, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Update an existing article.
     * @param id ID of the article to update.
     * @param article new article data from the request body.
     * @return updated article or 404 if not found.
     */
    @Operation(summary = "Update an Article by ID")
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody Article article) {
        System.out.println("DEBUG: Inside PUT /api/articles/" + id);
        System.out.println("DEBUG: Body = " + article);

        Article existing = articleService.findById(id);

        if (existing == null) {
            System.out.println("DEBUG: No article found to update.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existing.setTitle(article.getTitle());
        existing.setContent(article.getContent());
        existing.setPublished(article.isPublished());

        Article updated = articleService.save(existing);

        System.out.println("DEBUG: Updated article = " + updated);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Delete an Article by ID.
     * @param id ID of the article to delete.
     * @return HTTP 204 if deleted, 500 on error.
     */
    @Operation(summary = "Delete an Article by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable long id) {
        System.out.println("DEBUG: Inside DELETE /api/articles/" + id);

        try {
            articleService.deleteById(id);
            System.out.println("DEBUG: Deleted successfully.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("DEBUG: Exception in DELETE:");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete ALL articles in the system.
     * @return 204 on success, 500 on failure.
     */
    @Operation(summary = "Delete all Articles")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllArticles() {
        System.out.println("DEBUG: Inside DELETE /api/articles");

        try {
            articleService.deleteAll();
            System.out.println("DEBUG: All articles deleted.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("DEBUG: Exception deleting all:");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve all published articles (published = true).
     * @return list of published articles or 204 if none exist.
     */
    @Operation(summary = "Get all published Articles")
    @GetMapping("/published")
    public ResponseEntity<List<Article>> getPublishedArticles() {
        System.out.println("DEBUG: Inside GET /api/articles/published");

        List<Article> published = articleService.findByPublished(true);

        System.out.println("DEBUG: Published articles = " + published);

        return published.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(published, HttpStatus.OK);
    }
}

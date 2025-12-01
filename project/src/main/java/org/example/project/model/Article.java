package org.example.project.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * JPA Entity representing an Article in the system.
 *
 * - Annotated with @Entity so Spring Data JPA can map it to a database table.
 * - Swagger @Schema annotations provide metadata for API documentation.
 * - Contains basic fields: id, title, content, published.
 */
@Entity
@Schema(description = "Article entity representing content items")
public class Article {

    /**
     * Primary key for the Article table.
     *
     * - @Id marks this field as the unique identifier.
     * - @GeneratedValue(strategy = GenerationType.IDENTITY) lets the database
     *   auto-generate IDs (commonly using AUTO_INCREMENT).
     * - Using Long instead of primitive long ensures null safety before persistence.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            accessMode = Schema.AccessMode.READ_ONLY,
            description = "Unique ID of the article"
    )
    private Long id; // Use Long for JPA

    /**
     * Title of the article.
     * Stored as a simple String column in the database.
     */
    @Schema(description = "Title of the article")
    private String title;

    /**
     * Full content/body of the article.
     * Can be plain text or markdown depending on usage.
     */
    @Schema(description = "Content of the article")
    private String content;

    /**
     * Boolean flag indicating whether the article is published.
     * true = published, false = draft.
     */
    @Schema(description = "Published status of the article")
    private boolean published;

    /**
     * Default constructor required by JPA and frameworks.
     * Ensures the entity can be instantiated via reflection.
     */
    public Article() {}

    /**
     * Convenience constructor for quickly creating an Article instance.
     * Useful in tests or manual object creation.
     */
    public Article(String title, String content, boolean published) {
        this.title = title;
        this.content = content;
        this.published = published;
    }

    // ---------------------
    // Getters and Setters
    // ---------------------

    /** @return the unique ID of the article */
    public Long getId() {
        return id;
    }

    /** @param id sets the unique ID (usually handled by JPA) */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return the article title */
    public String getTitle() {
        return title;
    }

    /** @param title sets the article title */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the article content */
    public String getContent() {
        return content;
    }

    /** @param content sets the article content */
    public void setContent(String content) {
        this.content = content;
    }

    /** @return true if the article is published */
    public boolean isPublished() {
        return published;
    }

    /** @param published sets whether the article is published */
    public void setPublished(boolean published) {
        this.published = published;
    }

    /**
     * Provides a readable string representation of the Article.
     * Useful for logging, debugging, or console output.
     */
    @Override
    public String toString() {
        // Build and return a string that includes all key fields of the Article.

        return "Article [id=" + id +           // Include the article's unique ID
                ", title=" + title +           // Include the article's title
                ", content=" + content +       // Include the article's content/body
                ", published=" + published +   // Include whether the article is published (true/false)
                "]";
    }
}
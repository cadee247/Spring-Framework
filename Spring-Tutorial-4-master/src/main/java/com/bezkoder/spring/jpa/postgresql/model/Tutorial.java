package com.bezkoder.spring.jpa.postgresql.model;

import jakarta.persistence.*;

// Marks this class as a JPA entity mapped to a database table
@Entity
@Table(name = "tutorials") // Maps to the "tutorials" table in PostgreSQL
public class Tutorial {

    // Primary key with auto-generated value
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Column for tutorial title
    @Column(name = "title")
    private String title;

    // Column for tutorial description
    @Column(name = "description")
    private String description;

    // Column for published status (true/false)
    @Column(name = "published")
    private boolean published;

    // Default constructor required by JPA
    public Tutorial() {
    }

    // Constructor to initialize title, description, and published status
    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    // Getter for ID (read-only)
    public long getId() {
        return id;
    }

    // Getter and setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and setter for published status
    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean isPublished) {
        this.published = isPublished;
    }

    // Custom string representation for logging/debugging
    @Override
    public String toString() {
        return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
    }
}
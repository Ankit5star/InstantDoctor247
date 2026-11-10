package com.instantdoctor.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String summary;
    @Column(length = 2000)
    private String content;
    private String category;
    private String publishDate;
    private String readTime;
    private String imageUrl;

    public Article() {}

    public Article(Long id, String title, String summary, String content, String category, String publishDate, String readTime, String imageUrl) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.category = category;
        this.publishDate = publishDate;
        this.readTime = readTime;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPublishDate() { return publishDate; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

    public String getReadTime() { return readTime; }
    public void setReadTime(String readTime) { this.readTime = readTime; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}

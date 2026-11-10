package com.instantdoctor.controller;

import com.instantdoctor.entity.Article;
import com.instantdoctor.repository.ArticleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public List<Article> getArticles() {
        return articleRepository.findAll();
    }
}

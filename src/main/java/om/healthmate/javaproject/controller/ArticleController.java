package om.healthmate.javaproject.controller;

import om.healthmate.javaproject.entity.Article;
import om.healthmate.javaproject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("")
    public List<Article> getArticles(@RequestParam(value = "category", required = false) String category) {
        List<Article> articles = articleService.getAllArticles();
        if (category != null && !category.isEmpty() && !category.equals("all")) {
            articles = articles.stream()
                    .filter(a -> category.equalsIgnoreCase(a.getCategory()))
                    .collect(Collectors.toList());
        }
        // Sắp xếp mới nhất lên đầu (giả sử Article có trường createdAt kiểu Date hoặc LocalDateTime)
        articles = articles.stream()
                .sorted(Comparator.comparing(Article::getCreatedAt).reversed())
                .collect(Collectors.toList());
        return articles;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleService.getArticleById(id);
        return article.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleService.saveArticle(article);
    }

    @GetMapping("/categories")
    public Set<String> getCategories() {
        return articleService.getAllArticles().stream()
                .map(Article::getCategory)
                .filter(c -> c != null && !c.isEmpty())
                .collect(Collectors.toSet());
    }
} 
package om.healthmate.javaproject.controller;

import om.healthmate.javaproject.entity.Article;
import om.healthmate.javaproject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/articles")
public class ArticleViewController {
    private final ArticleService articleService;

    @Autowired
    public ArticleViewController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public String getArticleDetail(@PathVariable Long id, Model model) {
        Article article = articleService.getArticleById(id).orElse(null);
        if (article == null) {
            return "error/404";
        }
        model.addAttribute("article", article);
        List<Article> relatedArticles = articleService.getAllArticles().stream()
                .filter(a -> !a.getId().equals(id))
                .limit(3)
                .collect(Collectors.toList());
        model.addAttribute("relatedArticles", relatedArticles);
        return "articles/detail";
    }

    @GetMapping("/s_experiences")
    public String showExperiences(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "pages/s_experiences";
    }
} 
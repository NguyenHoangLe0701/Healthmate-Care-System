package om.healthmate.javaproject.repository;

import om.healthmate.javaproject.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Có thể thêm các phương thức custom query nếu cần
} 
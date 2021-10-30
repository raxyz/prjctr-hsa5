package com.prjctr.hsa.greeting.repository;

import java.util.List;

import com.prjctr.hsa.greeting.entity.Article;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleEsRepo extends ElasticsearchRepository<Article, String> {
    List<Article> findByTitle(String title);
}

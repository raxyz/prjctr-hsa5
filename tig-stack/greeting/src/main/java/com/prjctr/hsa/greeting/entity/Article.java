package com.prjctr.hsa.greeting.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "blog")
@NoArgsConstructor
@Data
public class Article {
    
    @Id
    private String id;
    private String title;

    public Article(String title) {
        this.title = title;
    }
}

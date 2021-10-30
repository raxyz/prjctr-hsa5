package com.prjctr.hsa.greeting.dto;

import java.util.List;

import com.prjctr.hsa.greeting.entity.Article;
import com.prjctr.hsa.greeting.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FullUser {
    private User user;
    private List<Article> article;
}

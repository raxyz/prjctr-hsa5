package com.prjctr.hsa.greeting.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.prjctr.hsa.greeting.dto.FullUser;
import com.prjctr.hsa.greeting.dto.UserDto;
import com.prjctr.hsa.greeting.entity.Article;
import com.prjctr.hsa.greeting.entity.User;
import com.prjctr.hsa.greeting.repository.ArticleEsRepo;
import com.prjctr.hsa.greeting.repository.UserMongoRepo;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserMongoRepo mongoRepo;
    private ArticleEsRepo articleRepo;

    public FullUser createUser(UserDto userDto) {
        User user = mongoRepo.save(new User(userDto.getFirstName(), userDto.getLastName()));
        Article article = articleRepo.save(new Article(userDto.getFirstName()));
        return new FullUser(user, Arrays.asList(article));
    }

    public List<FullUser> getAllUsers() {
        var users = mongoRepo.findAll()
            .stream()
            .map(u -> {
                var articles = articleRepo.findByTitle(u.getFirstName());
                return new FullUser(u, articles);
            })
            .collect(Collectors.toList());
        return users;
    }

    
}

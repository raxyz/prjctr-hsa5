package com.prjctr.hsa.greeting.controller;

import java.util.List;

import com.prjctr.hsa.greeting.dto.FullUser;
import com.prjctr.hsa.greeting.dto.UserDto;
import com.prjctr.hsa.greeting.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    public FullUser createUser(@RequestBody UserDto user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<FullUser> getAllUsers() {
        return userService.getAllUsers();
    }
}

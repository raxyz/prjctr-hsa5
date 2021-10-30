package com.prjctr.hsa.greeting.repository;

import com.prjctr.hsa.greeting.entity.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepo extends MongoRepository<User, String> {
}
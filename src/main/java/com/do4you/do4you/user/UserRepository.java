package com.do4you.do4you.user;

import com.do4you.do4you.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

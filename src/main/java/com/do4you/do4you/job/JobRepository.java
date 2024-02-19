package com.do4you.do4you.job;

import com.do4you.do4you.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JobRepository extends MongoRepository<Job, String> {
    Optional<Job> findById(String id);
}

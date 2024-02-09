package com.do4you.do4you.job;

import com.do4you.do4you.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {

}

package com.do4you.do4you.job;

import com.do4you.do4you.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    @Transactional(readOnly = true)
    public Job details(String id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> {
                    return new NoSuchElementException("해당 ID에 대한 작업을 찾을 수 없습니다.");
                });
    }
}

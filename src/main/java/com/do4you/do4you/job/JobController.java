package com.do4you.do4you.job;

import com.do4you.do4you.model.Job;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JobController {
    @Autowired
    private MongoClient client;

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/jobDetail.html")
    public String getJobDetail(Model model) {
        List<Job> jobList = jobRepository.findAll();
        System.out.println("jobList:" + jobList);
        model.addAttribute("jobs", jobList);
        return "jobDetail";
    }

    @GetMapping
    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }

    @GetMapping("/jobWrite.html")
    public String jobWrite(){
        return "jobWrite";
    }

    // 글 상세보기
    @GetMapping("/job/{id}")
//    public String getJobById(@PathVariable String id, Model model) {
    public String getJobById(Model model) {
        List<Job> jobList = new ArrayList<>();

//        MongoCollection<Document> collection = client.getDatabase("Do4You").getCollection("Job");
//        collection.find().map(Document::toJson).forEach(jobList::add());

        List<Job> jobLists = jobRepository.findAll();
        System.out.println("jobLists:"+jobLists);
        // MongoDB에서 작업을 가져옵니다.
//        Job job = jobRepository.findById(id).orElse(null);

//        System.out.println("22@@##@job:" + job);

//        model.addAttribute("job", job);
//        return job.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        return "jobDetail";
    }

    @PutMapping("/{id}")
    public Job updateJob(@PathVariable String id, @RequestBody Job job) {
        job.setId(id);
        return jobRepository.save(job);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable String id) {
        jobRepository.deleteById(id);
    }
}


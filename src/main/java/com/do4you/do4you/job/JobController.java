package com.do4you.do4you.job;

import com.do4you.do4you.dto.JobDto;
import com.do4you.do4you.dto.ResponseDto;
import com.do4you.do4you.model.Job;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class JobController {
    @Autowired
    private MongoClient client;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    // 글 상세보기
    @GetMapping("/jobList/{id}")
    public String findById(@PathVariable String id, Model model) {
        Job jobId = jobService.details(id);
        model.addAttribute("jobs", jobId);
        return "jobDetail";
    }

    @GetMapping("/jobList.html")
    public String getJobList(Model model) {
        List<Job> jobList = jobRepository.findAll();
        model.addAttribute("jobs", jobList);
        return "jobList";
    }

    @GetMapping
    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }

    @GetMapping("/jobWrite.html")
    public String jobWrite(){
        return "jobWrite";
    }

    @GetMapping("/job/{id}")
    public String getJobById(Model model) {
        List<Job> jobLists = jobRepository.findAll();
        System.out.println("jobLists:"+jobLists);
        return "jobDetail";
    }


    @GetMapping("/jobDetail/edit/{id}")
    public String editJob(@PathVariable String id, Model model) {
        System.out.println("jobRepository.findById(id).get():" +jobRepository.findById(id).get());
        model.addAttribute("jobs", jobRepository.findById(id).get());
        return "jobEdit";
    }

    // 글 수정
    @PostMapping("/jobDetail/{id}/update")
    @ResponseBody
    public ResponseDto<String> updateJob(@PathVariable String id, @RequestBody JobDto jobDto) {
        jobService.update(id, jobDto);
        return new ResponseDto<String>("ok", jobDto.getId());
    }

    // 글 삭제
    @DeleteMapping("/jobDetail/{id}/delete")
    public String deleteJob(@PathVariable String id) {
        jobService.delete(id);
        return "jobList";
    }
}


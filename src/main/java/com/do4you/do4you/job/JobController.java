package com.do4you.do4you.job;

import com.do4you.do4you.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @GetMapping
    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }

    @GetMapping("/{id}")
    public Job getJobById(@PathVariable String id) {
        return jobRepository.findById(id).orElse(null);
    }

    // 글쓰기
    @PostMapping("/write")
    public Job writeJob(@RequestBody Job job) {
        System.out.println("job content:" + job);
        return jobRepository.save(job);
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

//    @PostMapping("/job")
//    @ResponseBody
//    public String write(@RequestBody GeoDto geoDto){
//        Job job = new Job();
//        job.setTitle("New");
//        job.setLocation(new GeoJsonPoint(Double.parseDouble(geoDto.getLatitude()
//        ), Double.parseDouble(geoDto.getLongtitude())));
//        return jobRepository.save(job).getId();
//    }
}


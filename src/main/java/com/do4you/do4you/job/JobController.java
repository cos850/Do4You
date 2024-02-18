package com.do4you.do4you.job;

import com.do4you.do4you.dto.GeoDto;
import com.do4you.do4you.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
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
    public Job writeJob(@RequestBody Job job) throws Exception{
        // 사용자가 입력한 주소
        String addr = job.getLocation();
        LocationFind lf = new LocationFind();

        List<String> locationList = lf.getLocation(addr);

        GeoDto geoDto = new GeoDto();
        //위도 설정
        geoDto.setLatitude(String.valueOf(locationList.get(0)));
        //경도 설정
        geoDto.setLongitude(String.valueOf(locationList.get(1)));

        job.setGeoLocation(new GeoJsonPoint(Double.parseDouble(geoDto.getLatitude()
        ), Double.parseDouble(geoDto.getLongitude())));
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
}


package com.do4you.do4you.job;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class JobController {

    private final JobRepository jobRepository;

//    @GetMapping("/location")
//    public String test(String test){
//        System.out.println("test");
//        return "location";
//    }
//
//
//    @PostMapping("/job")
//    @ResponseBody
//    public String makeJob(@RequestBody GeoDto geoDto){
//        Job job = new Job();
//        job.setTitle("New");
//        job.setLocation(new GeoJsonPoint(Double.parseDouble(geoDto.getLatitude()
//        ), Double.parseDouble(geoDto.getLongtitude())));
//        return jobRepository.save(job).getId();
//    }
}


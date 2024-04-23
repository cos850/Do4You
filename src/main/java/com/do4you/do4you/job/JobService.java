package com.do4you.do4you.job;

import com.do4you.do4you.dto.JobDto;
import com.do4you.do4you.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    // 글 수정
    public String update(String id, JobDto jobDto) {
        System.out.println("jobDto:"+jobDto);
        // 현재 시간 가져오기
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String currentTime = format.format(date);
        System.out.println("currentTime:"+currentTime);

        Job jobList = Job.builder()
                .id(jobDto.getId())
                .title(jobDto.getTitle())
                .content(jobDto.getContent())
                .location(jobDto.getLocation())
                .reward_content(jobDto.getReward_content())
                .reward_type(jobDto.getReward_type())
                .update_at(currentTime)
                .geoLocation(new GeoJsonPoint(Double.parseDouble(jobDto.getGeoLocationX()
                ), Double.parseDouble(jobDto.getGeoLocationY())))
                .build();

        jobRepository.save(jobList);
        return "ok";
    }

    // 글 삭제
    @Transactional
    public void delete(String id) {
        jobRepository.deleteById(id);
    }
}

package com.do4you.do4you.job;

import com.do4you.do4you.dto.GeoDto;
import com.do4you.do4you.dto.JobDto;
import com.do4you.do4you.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    // 글 쓰기
    public String writeJob(Job job) throws Exception {
        GeoDto geoDto = location(job);
        job.setGeoLocation(new GeoJsonPoint(Double.parseDouble(geoDto.getLatitude()
        ), Double.parseDouble(geoDto.getLongitude())));
        job.setCreate_at(getTime());

        jobRepository.save(job);
        return "ok";
    }

    // 글 수정
    public String update(String id, JobDto jobDto) throws Exception {
        System.out.println("jobDto:"+jobDto);

        // 사용자가 입력한 주소
        String addr = jobDto.getLocation();
        LocationFind lf = new LocationFind();
        // 입력된 주소에서 위도, 경도 가져옴
        List<String> locationList = lf.getLocation(addr);

        System.out.println("@@@@locationList:"+ locationList);

        GeoDto geoDto = new GeoDto();
        //위도 설정
        geoDto.setLatitude(String.valueOf(locationList.get(0)));
        //경도 설정
        geoDto.setLongitude(String.valueOf(locationList.get(1)));


        Job jobList = Job.builder()
                .id(jobDto.getId())
                .title(jobDto.getTitle())
                .content(jobDto.getContent())
                .location(jobDto.getLocation())
                .reward_content(jobDto.getReward_content())
                .reward_type(jobDto.getReward_type())
                .update_at(getTime())
                .geoLocation(new GeoJsonPoint(Double.parseDouble(geoDto.getLatitude()
                ), Double.parseDouble(geoDto.getLongitude())))
                .build();

        jobRepository.save(jobList);
        return "ok";
    }

    // 글 삭제
    @Transactional
    public void delete(String id) {
        jobRepository.deleteById(id);
    }

    public GeoDto location(Job job) throws Exception {
        // 사용자가 입력한 주소
        String addr = job.getLocation();
        LocationFind lf = new LocationFind();

        // 입력된 주소에서 위도, 경도 가져옴
        List<String> locationList = lf.getLocation(addr);

        GeoDto geoDto = new GeoDto();
        //위도 설정
        geoDto.setLatitude(String.valueOf(locationList.get(0)));
        //경도 설정
        geoDto.setLongitude(String.valueOf(locationList.get(1)));

        return geoDto;
    }

    public String getTime(){
        //작성 시간 설정
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String currentTime = format.format(date);
        return currentTime;
    }

}

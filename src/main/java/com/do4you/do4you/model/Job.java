package com.do4you.do4you.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Document(collection = "Job")
public class Job {
    @Id
    private String id;
    private String title;
    private String content;
    private String view_num;
    private String create_at;
    private String update_at;
    private String reward_type;
    private String reward_content;
    // 사용자가 입력한 풀 주소
    private String location;

    // 주소의 위도, 경도
    private GeoJsonPoint geoLocation;
}

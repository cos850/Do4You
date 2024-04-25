package com.do4you.do4you.job;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationFind {

    public List<String> getLocation(String address) throws Exception{
        // 업로드 하기 전 확인 확인 확인
        // 네이버 geocode API 사용
        String clientId = "ftcvgn37nv";
        String clientSecret = "X49dQDaB49bP3yU5kTMoF221z0xkVquxB3DtoQm9";
        String inputLine;

        try {
            String addr = URLEncoder.encode(address, "UTF-8");
            // Geocoding 개요에 나와있는 API URL 입력.
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;	// JSON

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding 의 요청 헤더 입력
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            // 요청 결과 확인. 정상 호출인 경우 200
            int responseCode = con.getResponseCode();

            BufferedReader br;

            // 정상 호출이라면 읽어옴
            if (Integer.valueOf(responseCode) == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            StringBuffer response = new StringBuffer();

            while((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            br.close();

            JSONTokener tokener = new JSONTokener(response.toString());
            JSONObject object = new JSONObject(tokener);
            JSONArray arr = object.getJSONArray("addresses");
            List<String> location = new ArrayList<String>();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = (JSONObject) arr.get(i);
                location.add((String) temp.get("y"));
                location.add((String) temp.get("x"));
            }

            return location;

        }
        catch (Exception  e) {
            return Collections.singletonList(e.getMessage());
        }
    }
}

package se.kits.gakusei.kanjiclient.service;


import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class KanjiService {

    public ArrayList<String> lessons;

    public void printLessons() {
        lessons.forEach(lesson ->System.out.println(lesson));
    }

    public KanjiService(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                REST_SERVICE_URI+"/lessons?lessonType=kanji", String.class);
        String[] str = responseEntity.getBody().split("name\":\"");
        for (int i=1; i<str.length; i++){
            this.lessons.add(str[i].substring(0, str[i].indexOf('"')).replace(' ', '_'));
        }
    }

    private static final String REST_SERVICE_URI = "https://gakusei.daigaku.se/api";

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    public String getLesson(String lessonName){

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    REST_SERVICE_URI+"/questions/kanji?lessonName="+lessonName+"&username=test", String.class);
            return responseEntity.getBody();

        }catch (Exception e){
            System.out.println("Server response: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public String getAllLessons(){

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                REST_SERVICE_URI+"/lessons?lessonType=kanji", String.class);
        System.out.println(responseEntity.getBody());

        return responseEntity.getBody();
    }
}

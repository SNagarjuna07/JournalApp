package com.edigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edigest.journalApp.api.WeatherResponse;
import com.edigest.journalApp.cache.AppCache;

@Service
public class WeatherService {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;
    
    @Autowired
    private RestTemplate restTemplate;
     
    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) { 
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);

        if (weatherResponse != null) {
            return weatherResponse;
        } else {

            String finalAPI = appCache.AppCache.get("weather_api").replace("<city>", city).replace("<apiKey>", apiKey);
            
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            
            WeatherResponse body = response.getBody();

            if (body != null) {
                redisService.set("weather_of_" + city, body, 300l);
            }
            
        return body;
        }
    }
}

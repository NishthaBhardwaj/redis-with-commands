package com.basket.redis.weather.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WeatherService
{

   @Autowired
   ExternalService externalService;

   @Cacheable("weather")
   public List<String> getWeather(int id){
      System.out.println("calling getWeather----");
      return Collections.emptyList();
   }

   @Scheduled(fixedRate = 10_000000)
   public void update(){
      System.out.println("updating weather----");
      for (int i = 1; i <=5; i++)
      {
         externalService.getWeatherInfo(i);

      }

   }




}

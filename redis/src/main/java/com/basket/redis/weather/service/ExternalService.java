package com.basket.redis.weather.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class ExternalService
{

   @CachePut(value = "weather", key = "#id")
   public List<String> getWeatherInfo(int id){

      final int i = ThreadLocalRandom.current().nextInt(60, 100);
      return List.of(String.valueOf(i));

   }
}

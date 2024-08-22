package com.basket.redis.weather.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basket.redis.weather.service.WeatherService;

@RestController
@RequestMapping("weather")
public class WeatherController
{

   @Autowired
   private WeatherService weatherService;

   @GetMapping("{id}")
   public List<String> getWeather(@PathVariable int id){
      return weatherService.getWeather(id);
   }
}

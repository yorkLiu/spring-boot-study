package com.ly.weather.controller;

import com.ly.weather.service.WeatherDataService;
import com.ly.weather.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yongliu on 3/19/19.
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {
  
  @Autowired
  private WeatherDataService weatherDataService;
  
  @GetMapping("/cityId/{cityId}")
  public WeatherResponse getWeatherByCityId(@PathVariable("cityId") String cityId){
    return weatherDataService.getDataByCityId(cityId);
  }
  
  @GetMapping("/cityName/{cityName}")
  public WeatherResponse getWeatherByName(@PathVariable("cityName") String cityName){
    return weatherDataService.getDataByCityName(cityName);
  }
  
  
}

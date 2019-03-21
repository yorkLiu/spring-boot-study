package com.ly.weather.service;

import com.ly.weather.utils.XMLUtils;
import com.ly.weather.vo.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yongliu on 3/21/19.
 */

@Service
public class WeatherDataSyncServiceImp implements WeatherDataSyncService {
  
  private static final Logger logger = LoggerFactory.getLogger(WeatherDataSyncServiceImp.class);
  
  @Autowired
  private WeatherDataService weatherDataService;
  
  @Override
  public boolean syncWeatherData() throws Exception {

    logger.info("Start sync the weather data......");

    List<City> cityList = XMLUtils.loadCities();

    for (City city : cityList) {
      logger.info("Sync the cityId: {} - {}'s weather data", city.getCityId(), city.getCityName());
      weatherDataService.getDataByCityId(city.getCityId());
    }
    
    return true;
  }
}

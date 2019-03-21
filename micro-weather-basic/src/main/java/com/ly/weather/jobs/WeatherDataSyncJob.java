package com.ly.weather.jobs;

import com.ly.weather.service.WeatherDataSyncService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by yongliu on 3/21/19.
 */
public class WeatherDataSyncJob extends QuartzJobBean {
  
  private static Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);
  
  @Autowired
  private WeatherDataSyncService weatherDataSyncService;
  
  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    logger.info("Start the Weather Data Sync Job.");

    try {
      weatherDataSyncService.syncWeatherData();
    } catch (Exception e){
      logger.error("ERROR:", e.getMessage());
    }
  }
}

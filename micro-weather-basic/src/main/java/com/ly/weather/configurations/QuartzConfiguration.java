package com.ly.weather.configurations;

import com.ly.weather.jobs.WeatherDataSyncJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yongliu on 3/21/19.
 */
@Configuration
public class QuartzConfiguration {
  
  public static final int TIME_OUT=1800;
  
  
  // Job Detail
  @Bean
  public JobDetail jobDetail(){
    return JobBuilder.newJob(WeatherDataSyncJob.class).storeDurably().withIdentity("weatherDataSyncJobDetail")
      .build();
  }
  
  // Trigger
  @Bean
  public Trigger trigger(){

    SimpleScheduleBuilder scheduler = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(TIME_OUT)
      .repeatForever();
    
    return  TriggerBuilder.newTrigger().withIdentity("weatherDataSyncJobTrigger")
      .forJob(jobDetail())
      
      .withSchedule(scheduler)
      .build();
  }
}

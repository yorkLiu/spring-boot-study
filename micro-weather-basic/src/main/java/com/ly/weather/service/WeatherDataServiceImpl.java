package com.ly.weather.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.ly.weather.utils.StringUtil;
import com.ly.weather.vo.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * WeatherDataService 实现.
 * 
 * @since 1.0.0 2017年11月22日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {
	
	private static Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);
	
	private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";
	
	private static final Long TIME_OUT = 30L; // 30 seconds
	
	@Autowired
	private RestTemplate restTemplate;
	

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String uri = WEATHER_URI + "citykey=" + cityId;
		return this.doGetWeahter(uri);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		String uri = WEATHER_URI + "city=" + cityName;
		return this.doGetWeahter(uri);
	}
	
	private WeatherResponse doGetWeahter(String uri) {
		String key = uri;
		ObjectMapper mapper = new ObjectMapper();
		WeatherResponse resp = null;
		String strBody = null;

		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		
		if(stringRedisTemplate.hasKey(key)){
			logger.info("Load the weather data from the Redis.");
			strBody = ops.get(key);
			
		} else {
			logger.info("Redis don't have the data.");
			ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
			if (respString.getStatusCodeValue() == 200) {
				try {
					strBody = StringUtil.conventFromGzip(respString.getBody());
				}catch (Exception e){
					logger.error(e.getMessage(), e);
				}
				
				ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
			}
		}
		
		try {
			resp = mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return resp;
	}

}

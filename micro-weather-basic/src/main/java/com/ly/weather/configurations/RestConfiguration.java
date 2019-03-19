package com.ly.weather.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongliu on 3/19/19.
 */
@Configuration
public class RestConfiguration {
  

  @Bean
  public RestTemplate restTemplate() {

    RestTemplate restTemplate = new RestTemplate();
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
    messageConverters.addAll(restTemplate.getMessageConverters());
    for(HttpMessageConverter<?> converter :messageConverters) {
      if(converter instanceof StringHttpMessageConverter) {
        converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        break;
      }
    }
    restTemplate.setMessageConverters(messageConverters);
    return restTemplate;
  }
  
}

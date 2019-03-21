package com.ly.weather.utils;

import com.ly.weather.vo.City;
import com.ly.weather.vo.CityList;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

/**
 * Created by yongliu on 3/21/19.
 */
public class XMLUtils {
  
  public static Object xmlToObject(Class<?> clazz, File file) throws Exception{
    Object xmlObject = null;
    
    Reader reader = null;
    JAXBContext context = JAXBContext.newInstance(clazz);

    // XML 转为对象的接口
    Unmarshaller unmarshaller = context.createUnmarshaller();

    reader = new FileReader(file);
    xmlObject = unmarshaller.unmarshal(reader);

    if (null != reader) {
      reader.close();
    }

    return xmlObject;
  }
  
  public static List<City> loadCities() throws Exception{
    Resource resource = new ClassPathResource("city_list.xml");

    CityList cityList = (CityList)XMLUtils.xmlToObject(CityList.class, resource.getFile());
    
    return cityList.getCityList();
  }
}

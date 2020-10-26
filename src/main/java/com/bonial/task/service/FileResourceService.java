package com.bonial.task.service;

import com.bonial.task.exception.BusinessException;
import com.bonial.task.model.Restaurant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FileResourceService {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(FileResourceService.class);

    public static final String RESTAURANT_JSON_OBJECTS_PATH = "classpath:templates/*.json";

    public Map<Integer, Restaurant> readRestaurantsData() {
        try {
            Map<Integer, Restaurant> restaurantsMap = new HashMap<>();
            Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(RESTAURANT_JSON_OBJECTS_PATH);
            if(resources.length > 0) {
                for(Resource resource : resources) {
                    Restaurant restaurant = objectMapper.readValue(resource.getInputStream(), Restaurant.class);
                    String xAxis = restaurant.getCoordinate().split(",")[0].replaceFirst("[xX]=", "");
                    restaurantsMap.put(Integer.valueOf(xAxis), restaurant);
                }
                return restaurantsMap;
            } else {
                throw new BusinessException("Restaurant JSON Objects not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            throw new BusinessException("Incorrect JSON restaurant objects mapping", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

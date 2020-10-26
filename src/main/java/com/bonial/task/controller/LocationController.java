package com.bonial.task.controller;

import com.bonial.task.model.LocationResponse;
import com.bonial.task.model.Restaurant;
import com.bonial.task.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @GetMapping("/search_locations")
    public LocationResponse searchLocations(@RequestParam(name = "x") int x, @RequestParam(name = "y") int y) {
        logger.info("Processing Request");
        LocationResponse response = locationService.searchLocations(x,y);
        logger.info("Response Returned");
        return response;
    }

    @GetMapping("/location/{id}")
    public Restaurant getRestaurant(@PathVariable("id") String id) {
        logger.info("Processing Request");
        Restaurant response = locationService.getRestaurant(id);
        logger.info("Response Returned");
        return response;
    }
}

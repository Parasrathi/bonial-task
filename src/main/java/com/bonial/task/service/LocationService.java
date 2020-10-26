package com.bonial.task.service;

import com.bonial.task.exception.BusinessException;
import com.bonial.task.model.Coordinate;
import com.bonial.task.model.Location;
import com.bonial.task.model.LocationResponse;
import com.bonial.task.model.Restaurant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import static com.bonial.task.exception.ExceptionErrorCode.*;

@Service
public class LocationService {

    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    public static final int MAX_X = 14;
    public static final int MAX_Y = 10;
    public static final String RESTAURANT_JSON_OBJECTS_DIRECTORY = "classpath:templates/";
    public static final String FILE_EXTENSION = ".json";
    public static final String DECIMAL_FORMAT = "#.######";

    @Autowired
    private FileResourceService fileResourceService;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ObjectMapper objectMapper;

    public LocationResponse searchLocations(int x, int y) throws BusinessException {
        Coordinate userLocation = new Coordinate(x, y);
        Map<Integer, Double> restaurantDistanceMap = getNearByRestaurantLocations(userLocation);
        Map<Integer, Restaurant> restaurantsMap = fileResourceService.readRestaurantsData();

        return new LocationResponse("x=" + x + ",y=" + y, getLocationsData(restaurantDistanceMap, restaurantsMap));
    }

    private Map<Integer, Double> getNearByRestaurantLocations(Coordinate userLocation) {
        Map<Integer, Double> nearByRestaurants = new HashMap<>();
        getRestaurantsLocation().forEach(restaurantLocation -> {
            double distance = calculateDistance(restaurantLocation, userLocation);
            if (distance <= restaurantLocation.getX() && distance <= restaurantLocation.getY()) {
                nearByRestaurants.put((int) restaurantLocation.getX(), distance);
            }
        });
        return nearByRestaurants;
    }

    private List<Coordinate> getRestaurantsLocation() {
        int i = 1, j = 1;
        List<Coordinate> restaurantLocations = new ArrayList<>();
        while (i <= MAX_X && j <= MAX_Y) {
            restaurantLocations.add(new Coordinate(i++, j++));
        }
        return restaurantLocations;
    }

    private double calculateDistance(Coordinate c1, Coordinate c2) {
        return Math.sqrt(
                (c1.getX() - c2.getX()) * (c1.getX() - c2.getX()) +
                        (c1.getY() - c2.getY()) * (c1.getY() - c2.getY())
        );
    }

    private List<Location> getLocationsData(Map<Integer, Double> restaurantDistanceMap, Map<Integer, Restaurant> restaurantsMap) throws BusinessException {
        List<Location> locations = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        try {
            for (Map.Entry<Integer, Double> entry : restaurantDistanceMap.entrySet()) {
                Location location = convertToLocation(restaurantsMap.get(entry.getKey()));
                location.setDistance(Double.parseDouble(decimalFormat.format(entry.getValue())));
                locations.add(location);
            }
            locations.sort(Comparator.comparing(Location::getDistance).reversed());
            return locations;
        } catch (Exception ex) {
            throw new BusinessException(DECIMAL_PARSING_ERROR,ex);
        }
    }

    private Location convertToLocation(Restaurant restaurant) {
        Location location = new Location();
        if (restaurant.getName() != null) {
            location.setName(restaurant.getName());
        } else {
            location.setName(restaurant.getTitle());
        }
        location.setId(restaurant.getId());
        location.setCoordinate(restaurant.getCoordinate());
        return location;
    }

    public Restaurant getRestaurant(String id) throws BusinessException {
        try {
            Resource resource = resourceLoader.getResource(RESTAURANT_JSON_OBJECTS_DIRECTORY +id +FILE_EXTENSION);
            return objectMapper.readValue(resource.getInputStream(),Restaurant.class);
        } catch (FileNotFoundException ex) {
            throw new BusinessException(ENTITY_NOT_FOUND, ex);
        } catch (Exception ex) {
            throw new BusinessException(JSON_MAPPING_ERROR, ex);
        }
    }
}
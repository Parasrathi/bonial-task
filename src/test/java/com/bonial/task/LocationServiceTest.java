package com.bonial.task;

import com.bonial.task.exception.BusinessException;
import com.bonial.task.model.LocationResponse;
import com.bonial.task.model.Restaurant;
import com.bonial.task.service.FileResourceService;
import com.bonial.task.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
public class LocationServiceTest {

    @Test
    void contextLoads() {
    }

    @InjectMocks
    private LocationService locationServices;

    @Mock
    private FileResourceService fileResourceService;
    @Mock
    private ResourceLoader resourceLoader;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void searchLocations() throws BusinessException {
        int x = 1, y = 2;
        lenient().when(fileResourceService.readRestaurantsData()).thenReturn(readRestaurantData());
        LocationResponse response = locationServices.searchLocations(x,y);
        assert (response) != null;
        assert (response.getLocations()) != null;
        assert (response.getLocations().size()) == 5;
        assert (!response.getUserLocation().isEmpty());
    }

    @Test
    public void searchLocations2() throws BusinessException {
        int x = 100, y = 200;
        lenient().when(fileResourceService.readRestaurantsData()).thenReturn(readRestaurantData());
        LocationResponse response = locationServices.searchLocations(x,y);
        assert (response) != null;
        assert (response.getLocations()) != null;
        assert (response.getLocations().size()) == 0;
        assert (!response.getUserLocation().isEmpty());
    }

    @Test
    public void getRestaurant() throws BusinessException, IOException {
        String id = "19e1545c-8b65-4d83-82f9-7fcad4a23114";
        Resource mockResource = Mockito.mock(Resource.class);
        lenient().when(resourceLoader.getResource(Mockito.anyString())).thenReturn(mockResource);
        lenient().when(resourceLoader.getResource(Mockito.anyString()).getInputStream()).thenReturn(readInputStream(id));
        lenient().when(objectMapper.readValue((InputStream) Mockito.any(), Mockito.eq(Restaurant.class))).thenReturn(getRestaurantObject());
        Restaurant restaurant = locationServices.getRestaurant(id);
        assert (restaurant) != null;
        assert (restaurant.getId()).equals(id);
    }

    @Test
    public void getRestaurantbyInvalidId() throws IOException {
        String id = "123";
        Resource mockResource = Mockito.mock(Resource.class);
        try {
            lenient().when(resourceLoader.getResource(Mockito.anyString())).thenReturn(mockResource);
            lenient().when(resourceLoader.getResource(Mockito.anyString()).getInputStream()).thenReturn(readInputStream(id));
            lenient().when(objectMapper.readValue((InputStream) Mockito.any(), Mockito.eq(Restaurant.class))).thenReturn(null);
            Restaurant restaurant = locationServices.getRestaurant(id);
            assert (restaurant) == null;
        } catch (BusinessException ex) {
            assert(ex.getError().getCode()).endsWith(String.valueOf(HttpStatus.NOT_FOUND));
        }
    }

    private Map<Integer, Restaurant> readRestaurantData() {
        Map<Integer, Restaurant> restaurantsMap = new HashMap<>();
        for(int i=1; i<=10; i++)
            restaurantsMap.put(i,getRestaurantObject());
        return restaurantsMap;
    }

    private Restaurant getRestaurantObject() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Mantra Restaurant");
        restaurant.setType("Restaurant");
        restaurant.setId("19e1545c-8b65-4d83-82f9-7fcad4a23114");
        restaurant.setOpenningHours("10:00AM-10:00PM");
        restaurant.setImage("https://tinyurl.com");
        restaurant.setCoordinate("x=2,y=2");
        return  restaurant;
    }

    private InputStream readInputStream(String id) {
        return this.getClass().getResourceAsStream("/templates/" + id + ".json");
    }

}

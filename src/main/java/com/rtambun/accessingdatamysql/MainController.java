package com.rtambun.accessingdatamysql;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(path="/demo")
public class MainController {

    static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private LocationInfoRepository locationInfoRepository;

    @PostMapping(path = "/addLocationInfo")
    public @ResponseBody String addNewLocationInfo(@RequestParam String name,
                                                   @RequestParam String location,
                                                   @RequestParam float longitude,
                                                   @RequestParam float latitude) {

        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setName(name);
        locationInfo.setLocation(location);
        locationInfo.setLongitude(longitude);
        locationInfo.setLatitude(latitude);
        locationInfoRepository.save(locationInfo);
        logger.info("Data added to databse : " + locationInfo.toString());
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<LocationInfo> getAllData() {
        return locationInfoRepository.findAll();
    }

    @GetMapping(path = "/location")
    public @ResponseBody String getLocation(@RequestParam String location) {
        List<LocationInfo> locationInfoList = locationInfoRepository.findByLocation(location);

        ObjectMapper mapper = new ObjectMapper();
        String strResponse = null;

        try {
            strResponse = mapper.writeValueAsString(locationInfoList);
        } catch (JsonProcessingException jpe) {
            logger.info("Exception on serializing database search : " + jpe.getMessage());
        }
        logger.info("Search using location, locationInfo Json: " + strResponse);

        SearchResultByLocation searchByLocation = new SearchResultByLocation();
        searchByLocation.setLocationQuery(location);
        searchByLocation.setResult(locationInfoList);
        try {
            strResponse = mapper.writeValueAsString(searchByLocation);
        } catch (JsonProcessingException jpe) {
            logger.info("Exception on serializing response : " + jpe.getMessage());
            jpe.printStackTrace();
        }
        logger.info("Search using location, searchResult Json: " + strResponse);

        return strResponse;
    }

    @GetMapping(path = "/name")
    public @ResponseBody String getName(@RequestParam String name) {
        logger.info("Start query LocationInfo exact");
        List<LocationInfo> listInfoExact = locationInfoRepository.findByName(name);
        logger.info("LocationInfo exact = " + listInfoExact.size());

        logger.info("Start query LocationInfo match");
        List<LocationInfo> listInfoMatch = locationInfoRepository.findByNameIgnoreCase(name);
        logger.info("LocationInfo match = " + listInfoMatch.size());

        SearchResultByName searchResultByName = new SearchResultByName();
        searchResultByName.setExactMatchCount(listInfoExact.size());
        searchResultByName.setMatchCount(listInfoMatch.size());

        ObjectMapper mapper = new ObjectMapper();
        String response = null;
        try {
            response = mapper.writeValueAsString(searchResultByName);
        } catch (JsonProcessingException e) {
            logger.info("Exception on serializing the result name : " + e.getMessage());
            e.printStackTrace();
        }
        logger.info("Search using name, searchResult Json: " + response);

        return response;
    }

}

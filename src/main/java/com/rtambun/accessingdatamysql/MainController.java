package com.rtambun.accessingdatamysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/demo")
public class MainController {

    static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private LocationInfoRepository locationInfoRepository;

    @PostMapping(path = "/addLocationInfo")
    public @ResponseBody String addNewLocationInfo(@RequestParam String name,
                                                   @RequestParam String location,
                                                   @RequestParam String longitude,
                                                   @RequestParam String latitude) {

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
    public @ResponseBody
    List<LocationInfo> getLocation(@RequestParam String location) {
        return locationInfoRepository.findByLocation(location);
    }

}

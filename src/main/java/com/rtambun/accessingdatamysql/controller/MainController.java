package com.rtambun.accessingdatamysql.controller;

import com.rtambun.accessingdatamysql.dto.SearchResultByName;
import com.rtambun.accessingdatamysql.dto.LocationInfoByLocation;
import com.rtambun.accessingdatamysql.repository.LocationInfoRepository;
import com.rtambun.accessingdatamysql.service.GetResultByLocation;
import com.rtambun.accessingdatamysql.service.GetResultByName;
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

    @GetMapping(path = "/location")
    public @ResponseBody List<LocationInfoByLocation> getLocation() {
        GetResultByLocation getResultByLocation = new GetResultByLocation(locationInfoRepository);
        return getResultByLocation.getAll();
    }

    @GetMapping(path = "/location", params = {"lon", "lat"})
    public @ResponseBody List<LocationInfoByLocation> getLocation(@RequestParam(name = "lon") double longitude,
                                                                  @RequestParam(name = "lat") double latitude) {
        GetResultByLocation getResultByLocation = new GetResultByLocation(locationInfoRepository);
        return getResultByLocation.getByDistance(longitude, latitude);
    }

    @GetMapping(path = "/name")
    public @ResponseBody SearchResultByName getName(@RequestParam String name) {
        GetResultByName getResultByName = new GetResultByName(locationInfoRepository);
        return getResultByName.getResult(name);
    }

}

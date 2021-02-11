package com.rtambun.accessingdatamysql.service;

import com.rtambun.accessingdatamysql.dto.SearchResultByName;
import com.rtambun.accessingdatamysql.model.LocationInfo;
import com.rtambun.accessingdatamysql.repository.LocationInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GetResultByName {

    private Logger logger = LoggerFactory.getLogger(GetResultByName.class);

    private LocationInfoRepository locationInfoRepository;

    public GetResultByName(LocationInfoRepository locationInfoRepository) {
        this.locationInfoRepository = locationInfoRepository;
    }

    public SearchResultByName getResult (String name) {
        logger.info("Start query LocationInfo exact");
        List<LocationInfo> listInfoExact = locationInfoRepository.findByName(name);
        logger.info("LocationInfo exact = " + listInfoExact.size());

        logger.info("Start query LocationInfo match");
        List<LocationInfo> listInfoMatch = locationInfoRepository.findByNameContainingIgnoreCase(name);
        logger.info("LocationInfo match = " + listInfoMatch.size());

        SearchResultByName searchResultByName = new SearchResultByName();
        searchResultByName.setExactMatchCount(listInfoExact.size());
        searchResultByName.setMatchCount(listInfoMatch.size());

        return searchResultByName;
    }
}

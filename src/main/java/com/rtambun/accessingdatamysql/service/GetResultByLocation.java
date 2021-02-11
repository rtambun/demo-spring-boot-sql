package com.rtambun.accessingdatamysql.service;

import com.rtambun.accessingdatamysql.dto.LocationInfoByLocation;
import com.rtambun.accessingdatamysql.model.LocationInfo;
import com.rtambun.accessingdatamysql.repository.LocationInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GetResultByLocation {

    static final Logger logger = LoggerFactory.getLogger(GetResultByLocation.class);

    private LocationInfoRepository locationInfoRepository;

    public GetResultByLocation(LocationInfoRepository locationInfoRepository) {
        this.locationInfoRepository = locationInfoRepository;
    }

    public List<LocationInfoByLocation> getAll () {
//        return getAllDifferentWay(false, 0, 0);
        return formatResponseByLocation(locationInfoRepository.findAllOrderedByLocation());
    }

    public List<LocationInfoByLocation> getByDistance (double longitude, double latitude) {
//        return getAllDifferentWay(true, longitude, latitude);
        return formatResponseByLocation(locationInfoRepository.findByDistanceOrderedByLocation(latitude, longitude));
    }

    private List<LocationInfoByLocation> getAllDifferentWay (boolean isByDistance, double longitude, double latitude) {
        List<String> distinctLocationList = locationInfoRepository.findDistinctByLocation();
        String distinctLocation = "";

        List<LocationInfoByLocation> result = new ArrayList<LocationInfoByLocation>();
        for (String location: distinctLocationList) {
            LocationInfoByLocation locationInfoByLocation = new LocationInfoByLocation();
            locationInfoByLocation.setLocation(location);
            if (isByDistance) {
                locationInfoByLocation.setResult(locationInfoRepository.findByDistanceLocation(latitude, longitude, location));
            } else {
                locationInfoByLocation.setResult(locationInfoRepository.findByLocation(location));
            }
            result.add(locationInfoByLocation);
            distinctLocation += location;
        }

        logger.info("Size of distinct location: " + distinctLocationList.size());
        logger.info("Distinct location: " + distinctLocation);

        return result;
    }

    private List<LocationInfoByLocation> formatResponseByLocation (List<LocationInfo> result) {
//        LocationInfo[] resultArray = result.toArray(new LocationInfo[0]);
//        Arrays.sort(resultArray, locationInfoComparator);

        String location = null;
        int sizeDistinctLocation = 0;
        String distinctLocation = "";

        LocationInfoByLocation locationResult = null;
        ArrayList<LocationInfoByLocation> response = new ArrayList<LocationInfoByLocation>();

        for (LocationInfo locationInfo : result) {
            if (!locationInfo.getLocation().equals(location)) {
                //if location change commit location info by location to response list
                if (locationResult != null) {
                    response.add(locationResult);
                }
                location = locationInfo.getLocation();
                sizeDistinctLocation++;
                distinctLocation += location;
                locationResult = new LocationInfoByLocation();
                locationResult.setLocation(location);
            }
            locationResult.addResult(locationInfo);
        }

        //commit last locationResult if exist
        if (locationResult != null) {
            response.add(locationResult);
        }

        logger.info("Size of distinct location: " + sizeDistinctLocation);
        logger.info("Distinct location: " + distinctLocation);

        return response;
    }

    private static Comparator<LocationInfo> locationInfoComparator = new Comparator<LocationInfo>() {
        @Override
        public int compare(LocationInfo o1, LocationInfo o2) {
            return o1.getLocation().compareTo(o2.getLocation());
        }
    };

}

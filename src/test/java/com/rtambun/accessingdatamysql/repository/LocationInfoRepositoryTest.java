package com.rtambun.accessingdatamysql.repository;


import com.rtambun.accessingdatamysql.model.LocationInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.rtambun.accessingdatamysql.repository.LocationInfoTestSet;

import javax.xml.stream.Location;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LocationInfoRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private LocationInfoRepository locationInfoRepository;

    @Test
    public void testAddOneLocationInfoEntry () {
        ArrayList<LocationInfo> locationInfoList = new ArrayList<LocationInfo>();
        locationInfoList.add(LocationInfoTestSet.locationInfo1);

        locationInfoRepository.saveAll(locationInfoList);
        locationInfoRepository.flush();

        List<LocationInfo> locationInfoFromDatabase = locationInfoRepository.findAll();
        compareTwoLocationInfoLists(locationInfoList, locationInfoFromDatabase);
    }

    @Test
    public void testFindLocationInfoByNameEmptyList () {
        ArrayList<LocationInfo> locationInfoList = new ArrayList<LocationInfo>();

        locationInfoRepository.saveAll(locationInfoList);
        locationInfoRepository.flush();

        ArrayList<LocationInfo> locationInfoExpectedList = new ArrayList<LocationInfo>();

        List<LocationInfo> locationInfoFromDatabaseList = locationInfoRepository.findByName("Boeing");

        compareTwoLocationInfoLists(locationInfoExpectedList, locationInfoFromDatabaseList);
    }

    @Test
    public void testFindLocationInfoByName () {
        ArrayList<LocationInfo> locationInfoList = new ArrayList<LocationInfo>();
        locationInfoList.add(LocationInfoTestSet.locationInfo1);
        locationInfoList.add(LocationInfoTestSet.locationInfo2);

        locationInfoRepository.saveAll(locationInfoList);
        locationInfoRepository.flush();

        ArrayList<LocationInfo> locationInfoExpectedList = new ArrayList<LocationInfo>();
        locationInfoExpectedList.add(LocationInfoTestSet.locationInfo1);

        List<LocationInfo> locationInfoFromDatabaseList = locationInfoRepository.findByName("Boeing");

        compareTwoLocationInfoLists(locationInfoExpectedList, locationInfoFromDatabaseList);
    }

    @Test
    public void testFindDistinctByLocation () {
        ArrayList<LocationInfo> locationInfoList = new ArrayList<LocationInfo>();
        locationInfoList.add(LocationInfoTestSet.locationInfo1);
        locationInfoList.add(LocationInfoTestSet.locationInfo2);
        locationInfoList.add(LocationInfoTestSet.locationInfo5);

        locationInfoRepository.saveAll(locationInfoList);
        locationInfoRepository.flush();

        ArrayList<String> distinctLocationExpectedList = new ArrayList<String>();
        distinctLocationExpectedList.add("CNR");
        distinctLocationExpectedList.add("Jurong East");

        List<String> distinctLocationFromDatabaseList = locationInfoRepository.findDistinctByLocation();

        compareTwoStringLists(distinctLocationExpectedList, distinctLocationFromDatabaseList);
    }

    @Test
    public void testFindByLocation () {
        ArrayList<LocationInfo> locationInfoList = new ArrayList<LocationInfo>();
        locationInfoList.add(LocationInfoTestSet.locationInfo1);
        locationInfoList.add(LocationInfoTestSet.locationInfo2);
        locationInfoList.add(LocationInfoTestSet.locationInfo5);
        locationInfoList.add(LocationInfoTestSet.locationInfo3);
        locationInfoList.add(LocationInfoTestSet.locationInfo4);

        locationInfoRepository.saveAll(locationInfoList);
        locationInfoRepository.flush();

        ArrayList<LocationInfo> locationInfoExpectedList = new ArrayList<LocationInfo>();
        locationInfoExpectedList.add(LocationInfoTestSet.locationInfo1);
        locationInfoExpectedList.add(LocationInfoTestSet.locationInfo2);
        locationInfoExpectedList.add(LocationInfoTestSet.locationInfo3);
        locationInfoExpectedList.add(LocationInfoTestSet.locationInfo4);

        List<LocationInfo> locationInfoFromDatabaseList = locationInfoRepository.findByLocation("CNR");

        compareTwoLocationInfoLists(locationInfoExpectedList, locationInfoFromDatabaseList);
    }

    @Test
    public void testFindByDistanceOrderedByLocation () {
        ArrayList<LocationInfo> locationInfoList = new ArrayList<LocationInfo>();
        locationInfoList.add(LocationInfoTestSet.locationInfo1);
        locationInfoList.add(LocationInfoTestSet.locationInfo2);
        locationInfoList.add(LocationInfoTestSet.locationInfo5);
        locationInfoList.add(LocationInfoTestSet.locationInfo3);
        locationInfoList.add(LocationInfoTestSet.locationInfo4);
        locationInfoList.add(LocationInfoTestSet.locationInfo6);

        locationInfoRepository.saveAll(locationInfoList);
        locationInfoRepository.flush();

        ArrayList<LocationInfo> locationInfoExpectedList = new ArrayList<LocationInfo>();
        locationInfoExpectedList.add(LocationInfoTestSet.locationInfo1);
        locationInfoExpectedList.add(LocationInfoTestSet.locationInfo2);
        locationInfoExpectedList.add(LocationInfoTestSet.locationInfo3);
        locationInfoExpectedList.add(LocationInfoTestSet.locationInfo4);
        locationInfoExpectedList.add(LocationInfoTestSet.locationInfo6);


        List<LocationInfo> locationInfoFromDatabaseList = locationInfoRepository.findByDistanceOrderedByLocation(1.3, 103.2);

        compareTwoLocationInfoLists(locationInfoExpectedList, locationInfoFromDatabaseList);
    }

    private void compareTwoLocationInfoLists (List<LocationInfo> expected, List<LocationInfo> actual) {
        expected.stream().forEach(exp -> {
            System.out.println("Expected id: " + exp.getId());
            System.out.println("Expected location: " + exp.getLocation());
            System.out.println("Expected name: " + exp.getName());
            System.out.println("Expected latitude: " + exp.getLatitude());
            System.out.println("Expected longitude: " + exp.getLongitude());
        });

        actual.stream().forEach(act -> {
            System.out.println("Actual id: " + act.getId());
            System.out.println("Actual location: " + act.getLocation());
            System.out.println("Actual name: " + act.getName());
            System.out.println("Actual latitude: " + act.getLatitude());
            System.out.println("Actual longitude: " + act.getLongitude());
        });

        Iterator<LocationInfo> actIterator = actual.iterator();
        List<LocationInfo> result = expected.stream().filter(exp -> {
            LocationInfo act = actIterator.next();
            if (act != null) {
                return exp.getId() == act.getId() &&
                        exp.getName().compareTo(act.getName()) == 0 &&
                        exp.getLocation().compareTo(act.getLocation()) == 0 &&
                        exp.getLatitude() == act.getLatitude() &&
                        exp.getLongitude() == act.getLongitude();
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        Assert.isTrue(result.size() == expected.size(),
                "Result size of " + result.size() +
                " is not equal to the expected size of " + expected.size() +
                        ". Actual size is " + actual.size());
    }

    private void compareTwoStringLists (List<String> expected, List<String> actual) {
        List<String> result = expected.stream().filter(exp -> actual.stream().anyMatch(act -> {
            return (exp.compareTo(act) == 0);
        })).collect(Collectors.toList());
        Assert.isTrue(result.size() == expected.size(),
                "Expected size of " + result.size() +
                        " is not equal to the actual size of " + actual.size());
    }

}

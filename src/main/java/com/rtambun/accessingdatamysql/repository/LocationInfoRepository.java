package com.rtambun.accessingdatamysql.repository;

import com.rtambun.accessingdatamysql.model.LocationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.xml.stream.Location;
import java.util.List;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Integer> {

    @Query("select l from LocationInfo l where l.name=:name")
    List<LocationInfo> findByName (@Param("name") String name);

//    @Query("select l from LocationInfo l where UPPER(l.name) like UPPER(:name)")
    List<LocationInfo> findByNameContainingIgnoreCase (String name);

    @Query("select distinct l.location from LocationInfo l")
    List<String> findDistinctByLocation();

    @Query("select l from LocationInfo l where l.location=:location")
    List<LocationInfo> findByLocation (@Param("location") String location);

    @Query(value = "SELECT * FROM LocationInfo l " +
            "WHERE (3959 * acos (" +
            "cos ( radians(?1) ) " +
            "* cos ( radians (latitude) ) " +
            "* cos ( radians (longitude) - radians (?2) ) " +
            "+ sin ( radians (?1) ) " +
            "* sin ( radians (latitude) ) " +
            ") " +
            ")  < 2 and location = ?3", nativeQuery = true)
    List<LocationInfo> findByDistanceLocation (double lat, double lon, String location);

    @Query("select l from LocationInfo l order by l.location")
    List<LocationInfo> findAllOrderedByLocation();

    //SELECT id, (3959 * acos (cos ( radians(1.3) ) * cos ( radians (latitude) ) * cos ( radians (longitude) - radians (103.2) ) + sin ( radians (1.3) ) * sin ( radians (latitude) ) ) )  AS distance FROM locationinfo group by id;
    //SELECT * FROM locationinfo where (3959 * acos (cos ( radians(1.3) ) * cos ( radians (latitude) ) * cos ( radians (longitude) - radians (103.2) ) + sin ( radians (1.3) ) * sin ( radians (latitude) ) ) )  < 2;
    @Query(value = "SELECT * FROM LocationInfo l " +
            "WHERE (3959 * acos (" +
            "cos ( radians(?1) ) " +
            "* cos ( radians (latitude) ) " +
            "* cos ( radians (longitude) - radians (?2) ) " +
            "+ sin ( radians (?1) ) " +
            "* sin ( radians (latitude) ) " +
            ") " +
            ")  < 2 ORDER BY location", nativeQuery = true)
    List<LocationInfo> findByDistanceOrderedByLocation (double lat, double lon);
}

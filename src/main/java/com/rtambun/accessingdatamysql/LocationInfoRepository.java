package com.rtambun.accessingdatamysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Integer> {

    List<LocationInfo> findByLocation (String location);

    @Query("select l from LocationInfo l where l.name=:name")
    List<LocationInfo> findByName (@Param("name") String name);

    @Query("select l from LocationInfo l where UPPER(l.name)=UPPER(:name)")
    List<LocationInfo> findByNameIgnoreCase (@Param("name") String name);
}

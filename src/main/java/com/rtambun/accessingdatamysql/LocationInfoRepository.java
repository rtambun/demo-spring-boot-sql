package com.rtambun.accessingdatamysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.xml.stream.Location;
import java.util.List;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Integer> {

    List<LocationInfo> findByLocation (String location);

}

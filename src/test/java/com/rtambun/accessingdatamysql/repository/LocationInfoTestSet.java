package com.rtambun.accessingdatamysql.repository;

import com.rtambun.accessingdatamysql.model.LocationInfo;

public class LocationInfoTestSet {

    public static final LocationInfo locationInfo1 =
            new LocationInfo("Boeing", "CNR", 1.3, 103.2);
    public static final LocationInfo locationInfo2 =
            new LocationInfo("Thales", "CNR", 1.3, 103.2);
    public static final LocationInfo locationInfo3 =
            new LocationInfo("thales", "CNR", 1.3, 103.2);
    public static final LocationInfo locationInfo4 =
            new LocationInfo("Thales123", "CNR", 1.3, 103.2);
    public static final LocationInfo locationInfo5 =
            new LocationInfo("West Gate Mall", "Jurong East", 1.3, 103.5);
    public static final LocationInfo locationInfo6 =
            new LocationInfo("West Gate Mall", "Changi", 1.3, 103.199);
}

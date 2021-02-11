package com.rtambun.accessingdatamysql.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SearchResultByName {
    private int exactMatchCount;

    private int matchCount;
}

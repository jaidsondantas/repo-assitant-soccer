package com.assistant.main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Venue {
    private long id;
    private String name;
    private String shortName;
}

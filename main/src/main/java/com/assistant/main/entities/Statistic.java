package com.assistant.main.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "statistics")
public class Statistic {
    @Id
    private String id;
    private String name;
    private int categoryId;
    private String categoryName;
    private boolean isMajor;
    private String value;
    private double valuePercentage;
    private boolean isPrimary;
}

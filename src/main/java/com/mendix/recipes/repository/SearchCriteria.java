package com.mendix.recipes.repository;

import lombok.Builder;
import lombok.Data;

/**
 * SearchCriteria class
 */
@Data
@Builder
public class SearchCriteria {
        private String key;
        private String operation;
        private Object value;
}

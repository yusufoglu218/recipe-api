package com.mendix.recipes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**
 * Class for error details
 */
@Getter
@AllArgsConstructor
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
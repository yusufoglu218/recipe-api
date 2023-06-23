package com.mendix.recipes.exception;

/**
 * Enums for error description and internal error code
 */
public enum ErrorType {
    RECIPE_NOT_FOUND("Recipe not found with id: "),
    CATEGORY_NOT_FOUND("Category not found with id: ");

    private final String description;

    ErrorType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

}

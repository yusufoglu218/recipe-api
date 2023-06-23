package com.mendix.recipes.controller;

import com.mendix.recipes.exception.RecordNotFoundException;
import com.mendix.recipes.model.Category;
import com.mendix.recipes.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller class for category rest api
 */
@Tag(name = "Category-Controller", description = "Category Rest API")
@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get categories by one or more criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @GetMapping
    public Page<Category> getCategories(@Parameter(description = "Index of page") @RequestParam(defaultValue = "0") Integer pageNumber,
                                        @Parameter(description = "Size of records in the page") @RequestParam(defaultValue = "10") Integer pageSize,
                                        @Parameter(description = "The value to be sorted") @RequestParam(required = false) String sortBy) {
        return categoryService.getCategories(pageNumber, pageSize, sortBy);
    }

    @Operation(summary = "Get the category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "404", description = "Category not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RecordNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @GetMapping("{id}")
    public Category getCategoryById(@Parameter(description = "Id of the category") @PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @PostMapping
    public Category createCategory(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category object to save with json format")@Valid @RequestBody Category category) {
        return categoryService.saveCategory(category);
    }
    
    @Operation(summary = "Delete the category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "404", description = "Category not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RecordNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteCategoryById(@Parameter(description = "Id of the category") @PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update the category by id and category body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "404", description = "Category not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RecordNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @PutMapping("{id}")
    public Category updateCategoryById(
            @Parameter(description = "Id of the category") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category object to save with json format") @RequestBody Category categoryDetails) {

        return categoryService.updateCategory(id, categoryDetails);
    }

}

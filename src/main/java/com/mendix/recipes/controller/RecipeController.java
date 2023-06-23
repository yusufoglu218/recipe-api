package com.mendix.recipes.controller;


import com.mendix.recipes.exception.RecordNotFoundException;
import com.mendix.recipes.model.Recipe;
import com.mendix.recipes.service.RecipeService;
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
 * Controller class for recipe rest api
 */
@Tag(name = "Recipe-Controller", description = "Recipe Rest API")
@Slf4j
@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(summary = "Get recipes by one or more criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @GetMapping
    public Page<Recipe> getRecipes(@Parameter(description = "Category id") @RequestParam(required = false) Long categoryId,
                                   @Parameter(description = "Search criteria of the recipe") @RequestParam(required = false) String searchString,
                                   @Parameter(description = "Index of page") @RequestParam(defaultValue = "0") Integer pageNumber,
                                   @Parameter(description = "Size of records in the page") @RequestParam(defaultValue = "10") Integer pageSize,
                                   @Parameter(description = "The value to be sorted") @RequestParam(required = false) String sortBy) {

        return recipeService.getRecipeByCriteria(categoryId, searchString, pageNumber, pageSize, sortBy);
    }

    @Operation(summary = "Get the recipe by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))}),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RecordNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @GetMapping("{id}")
    public Recipe getRecipeById(@Parameter(description = "Id of the recipe") @PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @Operation(summary = "Create a new recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @PostMapping
    public Recipe createRecipe(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recipe object to save with json format")@Valid @RequestBody Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }

    @Operation(summary = "Delete the recipe by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))}),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RecordNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteRecipeById(@Parameter(description = "Id of the recipe") @PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update the recipe by id and recipe body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))}),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RecordNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @PutMapping("{id}")
    public Recipe updateRecipeById(
            @Parameter(description = "Id of the recipe") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recipe object to save with json format") @RequestBody Recipe recipeDetails) {

        return recipeService.updateRecipe(id, recipeDetails);
    }

}

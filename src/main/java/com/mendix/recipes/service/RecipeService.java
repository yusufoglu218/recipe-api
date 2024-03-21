package com.mendix.recipes.service;

import com.mendix.recipes.model.Recipe;
import org.springframework.data.domain.Page;

/**
 * Interface for recipe services.
 */
public interface RecipeService {

    /**
     * Get recipe/s by given parameters
     * @param searchCriteria
     * @return
     */
    Page<Recipe> getRecipeByCriteria(Long categoryId, String searchCriteria, int page, int size, String sortBy);

    /**
     * Get recipe by id
     * @param id
     * @return
     */
    Recipe getRecipeById(Long id);

    /**
     * Save recipe by the object parameter
     * @param recipe
     * @return
     */
    Recipe saveRecipe(Recipe recipe);

    /**
     * Update recipe by id and recipe object
     * @param id
     * @param recipe
     * @return
     */
    Recipe updateRecipe(Long id,Recipe recipe);

    /**
     * Delete recipe by id
     * @param id
     */
    void deleteRecipeById(Long id);

}

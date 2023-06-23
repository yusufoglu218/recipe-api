package com.mendix.recipes.repository;

import com.mendix.recipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository class for crud operations
 */
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
}

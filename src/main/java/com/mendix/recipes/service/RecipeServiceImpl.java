package com.mendix.recipes.service;

import com.mendix.recipes.exception.ErrorType;
import com.mendix.recipes.exception.RecordNotFoundException;
import com.mendix.recipes.model.Recipe;
import com.mendix.recipes.repository.RecipeRepository;
import com.mendix.recipes.repository.RecipeSpecificationsBuilder;
import com.mendix.recipes.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


/**
 * Implementation class for service layer of recipe operations.
 */
@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(Long id, Recipe recipeDetails) {
        Recipe recipeFromDb = getRecipeById(id);

        recipeFromDb.setTitle(recipeDetails.getTitle());
        recipeFromDb.setDirections(recipeDetails.getDirections());
        recipeFromDb.setYields(recipeDetails.getYields());
        recipeFromDb.getIngredients().clear();
        recipeFromDb.getIngredients().addAll(recipeDetails.getIngredients());
        recipeFromDb.setCategories(recipeDetails.getCategories());

        return recipeRepository.save(recipeFromDb);
    }

    @Override
    public void deleteRecipeById(Long id) {
        getRecipeById(id);
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> {
            log.error(ErrorType.RECIPE_NOT_FOUND + String.valueOf(id));
            return new RecordNotFoundException(ErrorType.RECIPE_NOT_FOUND + String.valueOf(id));
        });
    }

    @Override
    public Page<Recipe> getRecipeByCriteria(Long categoryId, String searchCriteria, int page, int size, String sortBy) {
        Pageable paging;

        if (StringUtils.isNotBlank(sortBy)) {
            paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(page, size);
        }

        Specification<Recipe> spec = null;

        if(categoryId != null){
            spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(Constants.RECIPE_CATEGORY_FIELD_NAME).get(Constants.CATEGORY_ID_COLUMN), categoryId));
        }

        if(StringUtils.isNotBlank(searchCriteria)) {
            RecipeSpecificationsBuilder builder = new RecipeSpecificationsBuilder();
            spec = spec == null ? builder.buildBySearchCriteria(searchCriteria) : spec.and(builder.buildBySearchCriteria(searchCriteria));
        }

        return recipeRepository.findAll(spec, paging);
    }

}

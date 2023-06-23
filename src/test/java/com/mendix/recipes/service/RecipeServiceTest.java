package com.mendix.recipes.service;

import com.mendix.recipes.constant.TestConstants;
import com.mendix.recipes.exception.ErrorType;
import com.mendix.recipes.exception.RecordNotFoundException;
import com.mendix.recipes.model.Recipe;
import com.mendix.recipes.repository.RecipeRepository;
import com.mendix.recipes.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for RecipeService
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeServiceImpl recipeService;

    private Recipe recipeMock;

    @BeforeAll
    public void createRecipeObjectOnInit() {
        recipeMock = TestUtil.createMockRecipe();
    }

    @Test
    public void getRecipeBySearchCriteria_OK() {
        Page<Recipe> pageMock = new PageImpl<>(List.of(TestUtil.createMockRecipe()));
        when(recipeRepository.findAll(Mockito.any(),Mockito.any(Pageable.class))).thenReturn(pageMock);

        Page pageFromService = recipeService.getRecipeByCriteria(TestConstants.CATEGORY_ID, TestConstants.RECIPE_SEARCH_STRING, TestConstants.PAGE_NUMBER,TestConstants.PAGE_SIZE,TestConstants.RECIPE_SORT_BY);

        Assertions.assertEquals(pageMock, pageFromService);
    }


    @Test
    public void getRecipeById_OK() {
        when(recipeRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(recipeMock));

        Recipe recipeFromService = recipeService.getRecipeById(TestConstants.RECIPE_ID);

        Assertions.assertEquals(recipeMock, recipeFromService);
    }

    @Test
    public void getRecipeById_NOT_FOUND() {
        when(recipeRepository.findById(Mockito.any())).thenThrow(new RecordNotFoundException(ErrorType.RECIPE_NOT_FOUND + String.valueOf(TestConstants.RECIPE_ID)));

        RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            recipeService.getRecipeById(TestConstants.RECIPE_ID);
        });

        assertEquals(ErrorType.RECIPE_NOT_FOUND + String.valueOf(TestConstants.RECIPE_ID), thrown.getMessage());
    }

    @Test
    public void deleteRecipeById_NOT_FOUND() {
        when(recipeRepository.findById(Mockito.any())).thenThrow(new RecordNotFoundException(ErrorType.RECIPE_NOT_FOUND + String.valueOf(TestConstants.RECIPE_ID)));

        RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            recipeService.deleteRecipeById(TestConstants.RECIPE_ID);
        });

        assertEquals(ErrorType.RECIPE_NOT_FOUND + String.valueOf(TestConstants.RECIPE_ID), thrown.getMessage());
    }


    @Test
    public void updateRecipe_OK() {
        when(recipeRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(recipeMock));
        when(recipeRepository.save(Mockito.any())).thenReturn(recipeMock);

        Recipe recipeFromService = recipeService.updateRecipe(TestConstants.RECIPE_ID, recipeMock);

        Assertions.assertEquals(recipeMock, recipeFromService);
    }

    @Test
    public void updateRecipe_NOT_FOUND() {
        when(recipeRepository.findById(Mockito.any())).thenThrow(new RecordNotFoundException(ErrorType.RECIPE_NOT_FOUND + String.valueOf(TestConstants.RECIPE_ID)));

        RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            recipeService.updateRecipe(TestConstants.RECIPE_ID, recipeMock);
        });

        assertEquals(ErrorType.RECIPE_NOT_FOUND + String.valueOf(TestConstants.RECIPE_ID), thrown.getMessage());
    }

    @Test
    public void createRecipe_OK() {
        when(recipeRepository.save(Mockito.any())).thenReturn(recipeMock);

        Recipe recipeFromService = recipeService.saveRecipe(recipeMock);

        Assertions.assertEquals(recipeMock, recipeFromService);
    }

}

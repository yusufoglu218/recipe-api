package com.mendix.recipes.util;

import com.mendix.recipes.constant.TestConstants;
import com.mendix.recipes.model.Category;
import com.mendix.recipes.model.Ingredient;
import com.mendix.recipes.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static Recipe createMockRecipe() {
        Ingredient ingredient = Ingredient.builder()
                .name(TestConstants.INGREDIENT_NAME)
                .amountQty(TestConstants.INGREDIENT_AMOUNT)
                .build();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);

        Category category = Category.builder()
                .name(TestConstants.CATEGORY_NAME)
                .build();

        List<Category> categories = new ArrayList<>();
        categories.add(category);


        Recipe recipe = Recipe.builder()
                .id(TestConstants.RECIPE_ID)
                .title(TestConstants.RECIPE_NAME)
                .directions(TestConstants.INSTRUCTIONS)
                .yields(TestConstants.NUMBER_OF_SERVING)
                .ingredients(ingredients)
                .categories(categories)
                .build();

        return recipe;
    }

    public static Category createMockCategory() {
        Category category = Category.builder()
                .name(TestConstants.CATEGORY_NAME)
                .build();

        return category;
    }

}

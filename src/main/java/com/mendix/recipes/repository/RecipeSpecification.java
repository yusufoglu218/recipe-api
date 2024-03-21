package com.mendix.recipes.repository;

import com.mendix.recipes.model.Category;
import com.mendix.recipes.model.Ingredient;
import com.mendix.recipes.model.Recipe;
import com.mendix.recipes.util.Constants;
import com.mendix.recipes.util.SpecSearchCriteria;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
public class RecipeSpecification implements Specification<Recipe> {

    private SpecSearchCriteria criteria;

    public RecipeSpecification(final SpecSearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SpecSearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate(final Root<Recipe> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
            case EQUALITY:
                if (Constants.CATEGORY_TABLE_NAME.equals(criteria.getKey())) {
                    Join<Recipe, Category> recipeCategoryJoin = root.join(Constants.RECIPE_CATEGORY_FIELD_NAME);
                    return builder.equal(recipeCategoryJoin.get(Constants.CATEGORY_NAME_COLUMN), criteria.getValue());
                } else if (Constants.INGREDIENT_TABLE_NAME.equals(criteria.getKey())) {
                    Join<Recipe, Category> recipeIngredientJoin = root.join(Constants.RECIPE_INGREDIENT_FIELD_NAME);
                    return builder.equal(recipeIngredientJoin.get(Constants.INGREDIENT_NAME_COLUMN), criteria.getValue());
                } else {
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            case STARTS_WITH:
                if (Constants.CATEGORY_TABLE_NAME.equals(criteria.getKey())) {
                    Join<Recipe, Category> recipeCategoryJoin = root.join(Constants.RECIPE_CATEGORY_FIELD_NAME);
                    return builder.like(recipeCategoryJoin.get(Constants.CATEGORY_NAME_COLUMN), criteria.getValue() + "%");
                } else if (Constants.INGREDIENT_TABLE_NAME.equals(criteria.getKey())) {
                    Join<Recipe, Ingredient> recipeIngredientJoin = root.join(Constants.RECIPE_INGREDIENT_FIELD_NAME);
                    return builder.like(recipeIngredientJoin.get(Constants.INGREDIENT_NAME_COLUMN), criteria.getValue() + "%");
                } else {
                    return builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
                }
            case ENDS_WITH:
                if (Constants.CATEGORY_TABLE_NAME.equals(criteria.getKey())) {
                    Join<Recipe, Category> recipeCategoryJoin = root.join(Constants.RECIPE_CATEGORY_FIELD_NAME);
                    return builder.like(recipeCategoryJoin.get(Constants.CATEGORY_NAME_COLUMN), "%" + criteria.getValue());
                } else if (Constants.INGREDIENT_TABLE_NAME.equals(criteria.getKey())) {
                    Join<Recipe, Ingredient> recipeIngredientJoin = root.join(Constants.RECIPE_INGREDIENT_FIELD_NAME);
                    return builder.like(recipeIngredientJoin.get(Constants.INGREDIENT_NAME_COLUMN), "%" + criteria.getValue());
                } else {
                    return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
                }
            case CONTAINS:
                if (Constants.CATEGORY_TABLE_NAME.equals(criteria.getKey())) {
                    Join<Recipe, Category> recipeCategoryJoin = root.join(Constants.RECIPE_CATEGORY_FIELD_NAME);
                    return builder.like(recipeCategoryJoin.get(Constants.CATEGORY_NAME_COLUMN), "%" + criteria.getValue() + "%");
                } else if (Constants.INGREDIENT_TABLE_NAME.equals(criteria.getKey())) {
                    Join<Recipe, Ingredient> recipeIngredientJoin = root.join(Constants.RECIPE_INGREDIENT_FIELD_NAME);
                    return builder.like(recipeIngredientJoin.get(Constants.INGREDIENT_NAME_COLUMN), "%" + criteria.getValue() + "%");
                } else {
                    return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
                }
            case NEGATION:
                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return builder.like(root.get(criteria.getKey()), criteria.getValue().toString());
            default:
                return null;

        }
    }
}

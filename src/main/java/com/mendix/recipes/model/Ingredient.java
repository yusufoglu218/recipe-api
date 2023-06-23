package com.mendix.recipes.model;

import com.mendix.recipes.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * Class for Ingredient
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = Constants.INGREDIENT_TABLE_NAME)
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "The ingredient.name is required.")
    private String name;

    private String amountQty;
    private String amountUnit;
}

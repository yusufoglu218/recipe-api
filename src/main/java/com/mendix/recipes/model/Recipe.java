package com.mendix.recipes.model;

import com.mendix.recipes.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Class for Recipe
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = Constants.RECIPE_TABLE_NAME)
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "The title is required.")
    @Column(unique = true, nullable = false)
    private String title;

    @NotBlank(message = "The directions is required.")
    @Column(columnDefinition = "TEXT")
    private String directions;

    @NotNull(message = "The yields is required.")
    @Positive(message = "The yields number should be positive.")
    @Column(nullable = false)
    private int yields;

    @Valid
    @NotNull(message = "The categories is required")
    @Column(nullable = false)
    @ManyToMany
    private List<Category> categories;

    @Valid
    @NotNull(message = "The ingredients is required")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipe_id")
    private List<Ingredient> ingredients;

}

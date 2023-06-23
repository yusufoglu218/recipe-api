package com.mendix.recipes.model;


import com.mendix.recipes.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * Class for Category
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = Constants.CATEGORY_TABLE_NAME)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "The category.name is required.")
    @Column(unique = true)
    private String name;
}

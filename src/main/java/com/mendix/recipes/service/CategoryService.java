package com.mendix.recipes.service;

import com.mendix.recipes.model.Category;
import org.springframework.data.domain.Page;

/**
 * Interface for category services.
 */
public interface CategoryService {

    /**
     * Get Category/s by given parameters
     * @return
     */
    Page<Category> getCategories(int page, int size, String sortBy);


    /**
     * Get category by id
     * @param id
     * @return
     */
    Category getCategoryById(Long id);

    /**
     * Save category by the object parameter
     * @param category
     * @return
     */
    Category saveCategory(Category category);

    /**
     * Update category by id and category object
     * @param id
     * @param category
     * @return
     */
    Category updateCategory(Long id,Category category);

    /**
     * Delete category by id
     * @param id
     */
    void deleteCategoryById(Long id);



}

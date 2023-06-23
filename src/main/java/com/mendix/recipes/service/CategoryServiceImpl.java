package com.mendix.recipes.service;

import com.mendix.recipes.exception.ErrorType;
import com.mendix.recipes.exception.RecordNotFoundException;
import com.mendix.recipes.model.Category;
import com.mendix.recipes.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


/**
 * Implementation class for service layer of category operations.
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Category> getCategories(int page, int size, String sortBy) {
        Pageable paging;

        if (StringUtils.isNotEmpty(sortBy)) {
            paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(page, size);
        }
        return categoryRepository.findAll(paging);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category categoryDetails) {
        Category categoryFromDb = getCategoryById(id);

        categoryFromDb.setName(categoryDetails.getName());
        return categoryRepository.save(categoryFromDb);
    }

    @Override
    public void deleteCategoryById(Long id) {
        getCategoryById(id);

        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            log.error(ErrorType.CATEGORY_NOT_FOUND + String.valueOf(id));
            return new RecordNotFoundException(ErrorType.CATEGORY_NOT_FOUND + String.valueOf(id));
        });
    }

}

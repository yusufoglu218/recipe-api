package com.mendix.recipes.service;

import com.mendix.recipes.constant.TestConstants;
import com.mendix.recipes.exception.ErrorType;
import com.mendix.recipes.exception.RecordNotFoundException;
import com.mendix.recipes.model.Category;
import com.mendix.recipes.repository.CategoryRepository;
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
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for CategoryService
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    private Category categoryMock;

    @BeforeAll
    public void createCategoryObjectOnInit() {
        categoryMock = TestUtil.createMockCategory();
    }

    @Test
    public void getCategoryById_OK() {
        when(categoryRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(categoryMock));

        Category categoryFromService = categoryService.getCategoryById(TestConstants.CATEGORY_ID);

        Assertions.assertEquals(categoryMock, categoryFromService);
    }

    @Test
    public void getCategoryById_NOT_FOUND() {
        when(categoryRepository.findById(Mockito.any())).thenThrow(new RecordNotFoundException(ErrorType.CATEGORY_NOT_FOUND + String.valueOf(TestConstants.CATEGORY_ID)));

        RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            categoryService.getCategoryById(TestConstants.CATEGORY_ID);
        });

        assertEquals(ErrorType.CATEGORY_NOT_FOUND + String.valueOf(TestConstants.CATEGORY_ID), thrown.getMessage());
    }

    @Test
    public void deleteCategoryById_NOT_FOUND() {
        when(categoryRepository.findById(Mockito.any())).thenThrow(new RecordNotFoundException(ErrorType.CATEGORY_NOT_FOUND + String.valueOf(TestConstants.CATEGORY_ID)));

        RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            categoryService.deleteCategoryById(TestConstants.CATEGORY_ID);
        });

        assertEquals(ErrorType.CATEGORY_NOT_FOUND + String.valueOf(TestConstants.CATEGORY_ID), thrown.getMessage());
    }


    @Test
    public void updateCategory_OK() {
        when(categoryRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(categoryMock));
        when(categoryRepository.save(Mockito.any())).thenReturn(categoryMock);

        Category categoryFromService = categoryService.updateCategory(TestConstants.CATEGORY_ID, categoryMock);

        Assertions.assertEquals(categoryMock, categoryFromService);
    }

    @Test
    public void updateCategory_NOT_FOUND() {
        when(categoryRepository.findById(Mockito.any())).thenThrow(new RecordNotFoundException(ErrorType.CATEGORY_NOT_FOUND + String.valueOf(TestConstants.CATEGORY_ID)));

        RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            categoryService.updateCategory(TestConstants.CATEGORY_ID, categoryMock);
        });

        assertEquals(ErrorType.CATEGORY_NOT_FOUND + String.valueOf(TestConstants.CATEGORY_ID), thrown.getMessage());
    }

    @Test
    public void createCategory_OK() {
        when(categoryRepository.save(Mockito.any())).thenReturn(categoryMock);

        Category categoryFromService = categoryService.saveCategory(categoryMock);

        Assertions.assertEquals(categoryMock, categoryFromService);
    }

    @Test
    public void getCategories_OK() {
        Page<Category> pageMock = new PageImpl<>(List.of(TestUtil.createMockCategory()));
        when(categoryRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageMock);

        Page pageFromService = categoryService.getCategories(TestConstants.PAGE_NUMBER,TestConstants.PAGE_SIZE,TestConstants.RECIPE_SORT_BY);

        Assertions.assertEquals(pageMock, pageFromService);
    }

}

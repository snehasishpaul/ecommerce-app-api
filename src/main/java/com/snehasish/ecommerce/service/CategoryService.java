package com.snehasish.ecommerce.service;

import com.snehasish.ecommerce.dto.CategoryDto;
import com.snehasish.ecommerce.dto.ResponseDto;

public interface CategoryService {
    ResponseDto createCategory(CategoryDto categoryDto);

    ResponseDto updateCategory(Long categoryId, CategoryDto categoryDto);

    ResponseDto getAllCategories();

    ResponseDto getCategoryById(Long categoryId);

    ResponseDto deleteCategory(Long categoryId);
}

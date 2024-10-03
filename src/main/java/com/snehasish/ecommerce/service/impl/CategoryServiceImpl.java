package com.snehasish.ecommerce.service.impl;

import com.snehasish.ecommerce.dto.CategoryDto;
import com.snehasish.ecommerce.dto.ResponseDto;
import com.snehasish.ecommerce.entity.Category;
import com.snehasish.ecommerce.exception.NotFoundException;
import com.snehasish.ecommerce.mapper.EntityDtoMapper;
import com.snehasish.ecommerce.repository.CategoryRepo;
import com.snehasish.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public ResponseDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        categoryRepo.save(category);
        return ResponseDto.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }

    @Override
    public ResponseDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
        category.setName(categoryDto.getName());
        categoryRepo.save(category);
        return ResponseDto.builder()
                .status(200)
                .message("Category updated successfully")
                .build();
    }

    @Override
    public ResponseDto getAllCategories() {
        List<Category> categoryList = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtoList = categoryList.stream().map(entityDtoMapper::mapCategoryToCategoryDto).toList();
        return ResponseDto.builder()
                .status(200)
                .categoryList(categoryDtoList)
                .build();
    }

    @Override
    public ResponseDto getCategoryById(Long categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
        CategoryDto categoryDto = entityDtoMapper.mapCategoryToCategoryDto(category);
        return ResponseDto.builder()
                .status(200)
                .category(categoryDto)
                .build();
    }

    @Override
    public ResponseDto deleteCategory(Long categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
        this.categoryRepo.delete(category);
        return ResponseDto.builder()
                .status(200)
                .message("Category deleted successfully")
                .build();
    }
}

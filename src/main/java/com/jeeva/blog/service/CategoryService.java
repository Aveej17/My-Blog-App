package com.jeeva.blog.service;

import com.jeeva.blog.model.Category;
import com.jeeva.blog.payload.CategoryDto;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(long categoryId);

    List<CategoryDto> getCategories();

    CategoryDto updateCategory(CategoryDto categoryDto, long categoryId);
}

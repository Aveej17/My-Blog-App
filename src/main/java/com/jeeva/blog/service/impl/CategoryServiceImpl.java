package com.jeeva.blog.service.impl;

import com.jeeva.blog.exception.ResourceNotFoundException;
import com.jeeva.blog.model.Category;
import com.jeeva.blog.payload.CategoryDto;
import com.jeeva.blog.repository.CategoryRepository;
import com.jeeva.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);

        CategoryDto savedCategoryDto = modelMapper.map(savedCategory, CategoryDto.class);
        return savedCategoryDto;
    }

    @Override
    public CategoryDto getCategory(long categoryId) {
        Category category= categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(categoryDto.getId());
        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategoryById(long categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.deleteById(categoryId);
    }
}

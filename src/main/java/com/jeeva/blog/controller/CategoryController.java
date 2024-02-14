package com.jeeva.blog.controller;

import com.jeeva.blog.payload.CategoryDto;
import com.jeeva.blog.service.CategoryService;
import com.jeeva.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Build Add category REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }


    //Build Get Category REST API
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable long id,
                                                   @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                   @RequestParam(value= "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                   @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
                                                   ){
        CategoryDto CategoryDto = categoryService.getCategory(id);
        return new ResponseEntity<>(CategoryDto, HttpStatus.OK);
    }

    // Get All Categories REST API

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return new ResponseEntity<>(categoryService.getCategories(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable long id, @RequestBody CategoryDto categoryDto){
        CategoryDto CategoryDto = categoryService.updateCategory(categoryDto, id);
        return new ResponseEntity<>(CategoryDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>("Category Deleted Successfully", HttpStatus.OK);
    }
}

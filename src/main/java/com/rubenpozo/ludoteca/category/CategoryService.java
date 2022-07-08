package com.rubenpozo.ludoteca.category;

import java.util.List;

import com.rubenpozo.ludoteca.category.model.Category;
import com.rubenpozo.ludoteca.category.model.CategoryDto;

public interface CategoryService {

    Category get(Long id);

    List<Category> findAll();

    void save(Long id, CategoryDto dto);

    void delete(Long id);

}

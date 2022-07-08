package com.rubenpozo.ludoteca.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubenpozo.ludoteca.category.model.Category;
import com.rubenpozo.ludoteca.category.model.CategoryDto;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category get(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) this.categoryRepository.findAll();
    }

    @Override
    public void save(Long id, CategoryDto dto) {

        Category categoria = null;

        if (id == null)
            categoria = new Category();
        else
            categoria = this.get(id);

        categoria.setName(dto.getName());

        this.categoryRepository.save(categoria);
    }

    @Override
    public void delete(Long id) {
        this.categoryRepository.deleteById(id);
    }
}

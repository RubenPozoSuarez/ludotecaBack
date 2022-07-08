package com.rubenpozo.ludoteca.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rubenpozo.ludoteca.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}

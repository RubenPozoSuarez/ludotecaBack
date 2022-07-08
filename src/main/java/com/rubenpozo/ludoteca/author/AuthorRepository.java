package com.rubenpozo.ludoteca.author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.rubenpozo.ludoteca.author.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Page<Author> findAll(Pageable pageable);

}

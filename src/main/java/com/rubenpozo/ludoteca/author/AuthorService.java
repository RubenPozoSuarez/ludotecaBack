package com.rubenpozo.ludoteca.author;

import java.util.List;

import org.springframework.data.domain.Page;

import com.rubenpozo.ludoteca.author.model.Author;
import com.rubenpozo.ludoteca.author.model.AuthorDto;
import com.rubenpozo.ludoteca.dto.PageableDto;

public interface AuthorService {

    Author get(Long id);

    Page<Author> findPage(PageableDto dto);

    void save(Long id, AuthorDto data);

    void delete(Long id);

    List<Author> findAll();
}

package com.rubenpozo.ludoteca.author;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.rubenpozo.ludoteca.author.model.Author;
import com.rubenpozo.ludoteca.author.model.AuthorDto;
import com.rubenpozo.ludoteca.author.model.AuthorSearchDto;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Author get(Long id) {
        return this.authorRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Author> findPage(AuthorSearchDto dto) {
        return this.authorRepository.findAll(dto.getPageable());
    }

    @Override
    public void save(Long id, AuthorDto data) {
        Author author = null;

        if (id != null)
            author = this.get(id);
        else
            author = new Author();

        BeanUtils.copyProperties(data, author, "id");

        this.authorRepository.save(author);
    }

    @Override
    public void delete(Long id) {
        this.authorRepository.deleteById(id);
    }

    @Override
    public List<Author> findAll() {
        return (List<Author>) this.authorRepository.findAll();
    }
}

package com.rubenpozo.ludoteca.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rubenpozo.ludoteca.author.model.Author;
import com.rubenpozo.ludoteca.author.model.AuthorDto;
import com.rubenpozo.ludoteca.author.model.AuthorSearchDto;
import com.rubenpozo.ludoteca.config.mapper.BeanMapper;

@RequestMapping(value = "/author")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @Autowired
    BeanMapper beanMapper;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<AuthorDto> findPage(@RequestBody AuthorSearchDto dto) {

        return this.beanMapper.mapPage(this.authorService.findPage(dto), AuthorDto.class);

    }

    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody AuthorDto data) {

        this.authorService.save(id, data);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {

        this.authorService.delete(id);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<AuthorDto> findAll() {

        List<Author> authors = this.authorService.findAll();

        return this.beanMapper.mapList(authors, AuthorDto.class);
    }
}

package com.rubenpozo.ludoteca.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devonfw.module.beanmapping.common.api.BeanMapper;
import com.rubenpozo.ludoteca.category.model.CategoryDto;

@RequestMapping(value = "/category")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    BeanMapper beanMapper;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CategoryDto> findAll() {

        return this.beanMapper.mapList(this.categoryService.findAll(), CategoryDto.class);
    }

    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody CategoryDto dto) {

        this.categoryService.save(id, dto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {

        this.categoryService.delete(id);

    }
}

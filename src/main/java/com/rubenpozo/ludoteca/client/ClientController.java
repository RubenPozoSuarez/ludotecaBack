package com.rubenpozo.ludoteca.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rubenpozo.ludoteca.client.model.ClientDto;
import com.rubenpozo.ludoteca.config.mapper.BeanMapper;

@RequestMapping(value = "/client")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    BeanMapper beanMapper;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ClientDto> findAll() {
        return this.beanMapper.mapList(clientService.findAll(), ClientDto.class);
    }

    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody ClientDto dto) {
        this.clientService.save(id, dto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        this.clientService.delete(id);
    }
}

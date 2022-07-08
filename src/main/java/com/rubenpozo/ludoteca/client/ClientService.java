package com.rubenpozo.ludoteca.client;

import java.util.List;

import com.rubenpozo.ludoteca.client.model.Client;
import com.rubenpozo.ludoteca.client.model.ClientDto;

public interface ClientService {
    Client get(Long id);

    List<Client> findAll();

    void save(Long id, ClientDto dto);

    void delete(Long id);
}

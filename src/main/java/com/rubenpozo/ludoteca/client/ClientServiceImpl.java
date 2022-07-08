package com.rubenpozo.ludoteca.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubenpozo.ludoteca.client.model.Client;
import com.rubenpozo.ludoteca.client.model.ClientDto;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client get(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public void save(Long id, ClientDto dto) {
        Client client = new Client();;

        if (id != null)
            client = this.get(id);

        client.setName(dto.getName());

        this.clientRepository.save(client);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

}

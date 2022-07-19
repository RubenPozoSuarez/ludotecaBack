package com.rubenpozo.ludoteca.client;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rubenpozo.ludoteca.client.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByName(String name);
}

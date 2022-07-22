package com.rubenpozo.ludoteca.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rubenpozo.ludoteca.client.model.Client;
import com.rubenpozo.ludoteca.client.model.ClientDto;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    public static final String CLIENT_NAME = "Pepe";
    public static final Long NOT_EXISTS_CLIENT_ID = 0L;
    public static final Long EXISTS_CLIENT_ID = 1L;

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientServiceImpl clientService;

    @Test
    public void findAllShouldReturnAllClients() {
        List<Client> listClients = new ArrayList<>();
        listClients.add(mock(Client.class));

        when(clientRepository.findAll()).thenReturn(listClients);
        List<Client> clients = clientService.findAll();

        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    @Test
    public void saveNotExistsClientIdShouldInsert() {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);
        clientService.save(null, clientDto);

        verify(clientRepository).save(client.capture());
        assertEquals(CLIENT_NAME, client.getValue().getName());
    }

    @Test
    public void saveExistsClientIdShouldUpdate() {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        Client client = mock(Client.class);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        clientService.save(EXISTS_CLIENT_ID, clientDto);

        verify(clientRepository).save(client);
    }

    @Test
    public void deleteExistsClientIdShouldDelete() {
        clientService.delete(EXISTS_CLIENT_ID);

        verify(clientRepository).deleteById(EXISTS_CLIENT_ID);
    }

    @Test
    public void getExistsClientIdShouldReturnClient() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(EXISTS_CLIENT_ID);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        Client clientResponse = clientService.get(EXISTS_CLIENT_ID);

        assertNotNull(clientResponse);
        assertEquals(EXISTS_CLIENT_ID, client.getId());
    }

    @Test
    public void getNotExistsClientIdShouldReturnNull() {
        when(clientRepository.findById(NOT_EXISTS_CLIENT_ID)).thenReturn(Optional.empty());

        Client client = clientService.get(NOT_EXISTS_CLIENT_ID);

        assertNull(client);
    }

    @Test
    public void testExistByName() {
        when(clientRepository.existsByName(CLIENT_NAME)).thenReturn(true);

        clientService.existsByName(CLIENT_NAME);

        verify(clientRepository).existsByName(CLIENT_NAME);
    }
}

package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {
    List<Client> getClients();
    List<ClientDTO> getClientsDto();
    Client getClientById(long id);
    ClientDTO getClientDto(Client client);
    Client getClientByEmail(String email);
    void saveClient(Client client);

}

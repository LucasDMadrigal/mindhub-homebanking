package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

//    private ClientRepository clientRepository;

    @Autowired
    ClientService clientService;
    @GetMapping("/")
        public ResponseEntity<?> getClients(Authentication authentication) {
        Client client = clientService.getClientByEmail(authentication.getName());
        String role = authentication.getAuthorities().toString();

        if (client == null || role != "ADMIN"){
            return new ResponseEntity<>("No tiene accesos", HttpStatus.FORBIDDEN);
        }

        List<ClientDTO> clientDTOList = clientService.getClientsDto();

        if (!clientDTOList.isEmpty()){
        return new ResponseEntity<>(clientDTOList, HttpStatus.OK);
        }else {
        return new ResponseEntity<>("No se encontraron clientes", HttpStatus.NOT_FOUND);

        }
    }
        @GetMapping("/{id}")
    public ResponseEntity<?> getClient (@PathVariable long id){
        Client client = clientService.getClientById(id);

            if (client != null){
                ClientDTO clientDto = new ClientDTO(client);
                return new ResponseEntity<>(clientDto, HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No se encontraron resultados", HttpStatus.NOT_FOUND);
            }

        }
}

package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
//    public String sayHello(){return "holis desde BK";}
        public ResponseEntity<?> getClients() {
        List<Client> clientList = clientRepository.findAll();
        List<ClientDTO> clientDTOList = clientList.stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());

        if (!clientList.isEmpty()){
        return new ResponseEntity<>(clientDTOList, HttpStatus.OK);
        }else {
        return new ResponseEntity<>("No se encontraron clientes", HttpStatus.NOT_FOUND);

        }
    }
        @GetMapping("/{id}")
    public ResponseEntity<?> getClient (@PathVariable long id){
        Client client = clientRepository.findById(id).orElse(null);

            if (client != null){
                ClientDTO clientDto = new ClientDTO(client);
                return new ResponseEntity<>(clientDto, HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No se encontraron resultados", HttpStatus.NOT_FOUND);

            }

        }
}

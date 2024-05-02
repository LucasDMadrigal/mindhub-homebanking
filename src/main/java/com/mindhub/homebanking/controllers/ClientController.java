package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/holis")
    public String sayHello(){return "holis desde BK";}

        @GetMapping("/getAllClients")
    public List<Client> clientList () {return clientRepository.findAll();}
}

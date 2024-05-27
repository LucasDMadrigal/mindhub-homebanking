package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.LoginDTO;
import com.mindhub.homebanking.DTO.RegisterDTO;
import com.mindhub.homebanking.ServicesSecurity.JwtUtilService;
import com.mindhub.homebanking.Utils.GenerateAccountNumber;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    JwtUtilService jwtUtilService;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDTO loginDTO){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
            final String jwt = jwtUtilService.generateToken(userDetails);

            return ResponseEntity.ok(jwt);
        }
        catch (Exception e){
            return new ResponseEntity<>("algo anda mal",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){

        if (registerDTO.firstName() == null || registerDTO.firstName().isBlank()) {
            return new ResponseEntity<>("nombre vacio",HttpStatus.BAD_REQUEST);
        }

        if (registerDTO.lastName() == null || registerDTO.lastName().isBlank()) {
            return new ResponseEntity<>("apellido vacio",HttpStatus.BAD_REQUEST);
        }

        if (registerDTO.email() == null || registerDTO.email().isBlank()) {
            return new ResponseEntity<>("email vacio",HttpStatus.BAD_REQUEST);
        }

        if (registerDTO.password() == null || registerDTO.password().isBlank()) {
            return new ResponseEntity<>("password vacio",HttpStatus.BAD_REQUEST);
        }
        Client client = new Client(
                registerDTO.firstName(),
                registerDTO.lastName(),
                registerDTO.email(),
                passwordEncoder.encode(registerDTO.password())
        );

        String accountNumber = GenerateAccountNumber.accountNumber();
        clientRepository.save(client);

        Account newAccount = new Account(accountNumber, LocalDate.now(), 5000.0, client);
        accountRepository.save(newAccount);

        return new ResponseEntity<>("Usuario creado",HttpStatus.CREATED);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return ResponseEntity.ok(new ClientDTO(client));
    }


}

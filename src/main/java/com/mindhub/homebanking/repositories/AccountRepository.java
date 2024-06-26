package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(String number);
}

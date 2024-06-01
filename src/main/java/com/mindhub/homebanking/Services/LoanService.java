package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    List<Loan> getLoans();
    List<LoanDTO> getLoansDto();
    Loan getLoanById(long id);

}

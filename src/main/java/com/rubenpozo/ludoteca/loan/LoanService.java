package com.rubenpozo.ludoteca.loan;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.rubenpozo.ludoteca.loan.model.Loan;
import com.rubenpozo.ludoteca.loan.model.LoanDto;
import com.rubenpozo.ludoteca.loan.model.LoanSearchDto;

public interface LoanService {
    Page<Loan> find(Long game, Long client, LocalDate date, LoanSearchDto dto);

    void save(LoanDto data);

    void delete(Long id);
}

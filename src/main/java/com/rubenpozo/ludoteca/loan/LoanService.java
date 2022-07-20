package com.rubenpozo.ludoteca.loan;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.rubenpozo.ludoteca.dto.PageableDto;
import com.rubenpozo.ludoteca.loan.model.Loan;
import com.rubenpozo.ludoteca.loan.model.LoanDto;

public interface LoanService {
    Page<Loan> find(Long game, Long client, LocalDate date, PageableDto dto);

    void save(LoanDto data);

    void delete(Long id);

    boolean numberDaysBetweenDates(LocalDate startDate, LocalDate repaymentDate);

    boolean gameAvailableForDays(LoanDto loanDto);

    boolean customerGamesByDate(LoanDto loanDto);
}

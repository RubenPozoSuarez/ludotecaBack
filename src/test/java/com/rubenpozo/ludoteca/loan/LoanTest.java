package com.rubenpozo.ludoteca.loan;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.rubenpozo.ludoteca.dto.PageableDto;
import com.rubenpozo.ludoteca.loan.model.Loan;

@ExtendWith(MockitoExtension.class)
public class LoanTest {

    public static final String CATEGORY_NAME = "CAT1";
    public static final String AUTHOR_NAME = "Jose";
    public static final String GAME_NAME = "EL juego";
    public static final Long ID = 1L;
    public static final String CLIENT_NAME = "Pepe";

    @Mock
    LoanRepository loanRepository;

    @InjectMocks
    LoanServiceImpl loanService;

    @Test
    public void findAllShouldReturnAllLoans() {
        List<Loan> listLoans = new ArrayList<>();
        listLoans.add(mock(Loan.class));

        when(loanRepository.find(null, null, null)).thenReturn(listLoans);

        Sort sortByName = Sort.by("startDate");
        Pageable pageable = PageRequest.of(0, 5, sortByName);
        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageable(pageable);

        Page<Loan> pageLoans = loanService.find(null, null, null, pageableDto);

        assertNotNull(pageLoans);
    }
}

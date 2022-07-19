package com.rubenpozo.ludoteca.loan;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.rubenpozo.ludoteca.client.ClientService;
import com.rubenpozo.ludoteca.game.GameService;
import com.rubenpozo.ludoteca.loan.model.Loan;
import com.rubenpozo.ludoteca.loan.model.LoanDto;
import com.rubenpozo.ludoteca.loan.model.LoanSearchDto;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    GameService gameService;

//    @Override
//    public List<Loan> find(Long game, Long client, LocalDate date) {
//        return loanRepository.find(game, client, date);
//    }

    @Override
    public Page<Loan> find(Long game, Long client, LocalDate date, LoanSearchDto dto) {
        List<Loan> loans = loanRepository.find(game, client, date);

        final int start = (int) dto.getPageable().getOffset();
        final int end = Math.min(start + dto.getPageable().getPageSize(), loans.size());
        final Page<Loan> pageLoans = new PageImpl<>(loans.subList(start, end), dto.getPageable(), loans.size());
        return pageLoans;
    }

    @Override
    public void delete(Long id) {
        loanRepository.deleteById(id);
    }

    @Override
    public void save(LoanDto data) {
        Loan loan = new Loan();

        BeanUtils.copyProperties(data, loan, "id");

        loan.setClient(clientService.get(data.getClient().getId()));
        loan.setGame(gameService.get(data.getGame().getId()));

        this.loanRepository.save(loan);

    }
}

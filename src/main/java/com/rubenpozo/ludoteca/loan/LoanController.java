package com.rubenpozo.ludoteca.loan;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rubenpozo.ludoteca.config.mapper.BeanMapper;
import com.rubenpozo.ludoteca.loan.model.LoanDto;
import com.rubenpozo.ludoteca.loan.model.LoanSearchDto;

@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    BeanMapper beanMapper;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<LoanDto> find(@RequestParam(value = "game", required = false) Long idGame,
            @RequestParam(value = "client", required = false) Long idClient,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody LoanSearchDto dto) {

        /*
         * List<Loan> loans = loanService.find(idGame, idClient, date);
         * 
         * return beanMapper.mapList(loans, LoanDto.class);
         */
        return beanMapper.mapPage(this.loanService.find(idGame, idClient, date, dto), LoanDto.class);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public void save(@RequestBody LoanDto data) {

        this.loanService.save(data);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {

        this.loanService.delete(id);
    }

}

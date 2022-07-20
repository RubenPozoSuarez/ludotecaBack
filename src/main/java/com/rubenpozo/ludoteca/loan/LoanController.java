package com.rubenpozo.ludoteca.loan;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rubenpozo.ludoteca.config.mapper.BeanMapper;
import com.rubenpozo.ludoteca.dto.MessageDto;
import com.rubenpozo.ludoteca.dto.PageableDto;
import com.rubenpozo.ludoteca.loan.model.LoanDto;

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
            @RequestBody PageableDto dto) {

        return beanMapper.mapPage(this.loanService.find(idGame, idClient, date, dto), LoanDto.class);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public ResponseEntity<?> save(@RequestBody LoanDto data) {
        if (loanService.numberDaysBetweenDates(data.getStartDate(), data.getRepaymentDate()))
            return messagesValidation("El préstamo del juego no puede ser superior a 14 días");

        if (loanService.gameAvailableForDays(data))
            return messagesValidation("Para la fecha de inicio seleccionada, el juego esta prestado a otro cliente");

        if (loanService.customerGamesByDate(data))
            return messagesValidation("El cliente tiene prestado dos juegos en la fecha inicial seleccionada");

        this.loanService.save(data);
        return new ResponseEntity<>(new MessageDto("Préstamo guardado"), HttpStatus.CREATED);
    }

    private ResponseEntity<?> messagesValidation(String message) {
        return new ResponseEntity<>(new MessageDto(message), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        this.loanService.delete(id);
    }

}

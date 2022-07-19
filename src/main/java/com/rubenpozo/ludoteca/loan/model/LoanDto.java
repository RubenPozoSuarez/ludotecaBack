package com.rubenpozo.ludoteca.loan.model;

import java.time.LocalDate;

import com.rubenpozo.ludoteca.client.model.ClientDto;
import com.rubenpozo.ludoteca.game.model.GameDto;

public class LoanDto {
    private Long id;

    private GameDto game;

    private ClientDto client;

    private LocalDate startDate;

    private LocalDate repaymentDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(LocalDate repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

}

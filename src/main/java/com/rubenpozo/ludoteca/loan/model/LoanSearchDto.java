package com.rubenpozo.ludoteca.loan.model;

import org.springframework.data.domain.Pageable;

public class LoanSearchDto {
    private Pageable pageable;

    public Pageable getPageable() {
        return this.pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}

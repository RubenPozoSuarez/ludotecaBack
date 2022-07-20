package com.rubenpozo.ludoteca.dto;

import org.springframework.data.domain.Pageable;

public class PageableDto {
    private Pageable pageable;

    public Pageable getPageable() {
        return this.pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}

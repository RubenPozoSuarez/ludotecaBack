package com.rubenpozo.ludoteca.author.model;

import org.springframework.data.domain.Pageable;

public class AuthorSearchDto {

    private Pageable pageable;

    public Pageable getPageable() {
        return this.pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}

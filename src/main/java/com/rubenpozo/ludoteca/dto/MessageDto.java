package com.rubenpozo.ludoteca.dto;

public class MessageDto {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageDto(String message) {
        super();
        this.message = message;
    }

}

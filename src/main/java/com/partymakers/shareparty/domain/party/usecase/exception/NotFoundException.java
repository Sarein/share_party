package com.partymakers.shareparty.domain.party.usecase.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() { super();}

    public NotFoundException(String description) {
        super(description);
    }
}

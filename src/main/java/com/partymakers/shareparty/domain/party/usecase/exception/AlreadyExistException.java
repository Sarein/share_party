package com.partymakers.shareparty.domain.party.usecase.exception;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException() { super();}
    public AlreadyExistException(String description) {super(description);}
}

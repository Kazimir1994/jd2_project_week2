package ru.kazimir.bortnik.jb.controller.exceptions;

public class NullReturningDocumentDTOException extends RuntimeException {
    public NullReturningDocumentDTOException(String s) {
        super(s);
    }
}
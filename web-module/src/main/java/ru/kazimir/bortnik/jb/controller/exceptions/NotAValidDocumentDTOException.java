package ru.kazimir.bortnik.jb.controller.exceptions;

public class NotAValidDocumentDTOException extends RuntimeException {
    public NotAValidDocumentDTOException(String s) {
        super(s);
    }
}
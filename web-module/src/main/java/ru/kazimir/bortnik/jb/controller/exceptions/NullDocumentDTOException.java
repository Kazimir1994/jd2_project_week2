package ru.kazimir.bortnik.jb.controller.exceptions;

public class NullDocumentDTOException extends RuntimeException {
    public NullDocumentDTOException(String s) {
        super(s);
    }
}
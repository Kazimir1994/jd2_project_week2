package ru.kazimir.bortnik.jb.controller.validations;

import ru.kazimir.bortnik.jb.databaseservice.model.DocumentDTO;

import java.util.Map;

public interface ValidationDocumentController {

    Map<String, String> validateReturningDocumentDTO(DocumentDTO returningDocumentDTO);

    Map<String, String> validateArgumentDocumentDTO(DocumentDTO incomingDocumentDTO);

    Map<String, String> validateID(Long id);
}

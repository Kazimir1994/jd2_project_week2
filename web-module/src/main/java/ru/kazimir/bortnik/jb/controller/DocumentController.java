package ru.kazimir.bortnik.jb.controller;

import ru.kazimir.bortnik.jb.databaseservice.model.DocumentDTO;

public interface DocumentController {
    DocumentDTO add(DocumentDTO documentDTO);

    DocumentDTO getDocumentById(Long id);

    void delete(Long id);
}

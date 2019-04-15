package ru.kazimir.bortnik.jb.databaseservice;

import ru.kazimir.bortnik.jb.databaseservice.model.DocumentDTO;

public interface DocumentService {
    DocumentDTO add(DocumentDTO documentDTO);

    DocumentDTO getDocumentById(Long id);

    void delete(Long id);
}

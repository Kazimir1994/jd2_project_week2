package ru.kazimir.bortnik.jb.databaseservice.converter;

import ru.kazimir.bortnik.jb.databaserepository.model.Document;
import ru.kazimir.bortnik.jb.databaseservice.model.DocumentDTO;

public interface DocumentConverter {

    Document fromDTO(DocumentDTO documentDTO);

    DocumentDTO toDTO(Document document);
}

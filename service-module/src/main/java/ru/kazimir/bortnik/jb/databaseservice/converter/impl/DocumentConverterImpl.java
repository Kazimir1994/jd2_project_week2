package ru.kazimir.bortnik.jb.databaseservice.converter.impl;

import org.springframework.stereotype.Component;
import ru.kazimir.bortnik.jb.databaserepository.model.Document;
import ru.kazimir.bortnik.jb.databaseservice.converter.DocumentConverter;
import ru.kazimir.bortnik.jb.databaseservice.model.DocumentDTO;

@Component("documentConverter")
public class DocumentConverterImpl implements DocumentConverter {

    @Override
    public Document fromDTO(DocumentDTO documentDTO) {
        Document document = new Document();
        document.setUniqueNumber(documentDTO.getUniqueNumber());
        document.setDescription(documentDTO.getDescription());
        return document;
    }

    @Override
    public DocumentDTO toDTO(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(document.getId());
        documentDTO.setUniqueNumber(document.getUniqueNumber());
        documentDTO.setDescription(document.getDescription());
        return documentDTO;
    }
}

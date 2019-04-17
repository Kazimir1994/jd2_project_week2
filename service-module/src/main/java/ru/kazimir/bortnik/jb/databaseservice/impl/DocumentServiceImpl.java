package ru.kazimir.bortnik.jb.databaseservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.kazimir.bortnik.jb.databaserepository.DocumentRepository;
import ru.kazimir.bortnik.jb.databaserepository.model.Document;
import ru.kazimir.bortnik.jb.databaseservice.DocumentService;
import ru.kazimir.bortnik.jb.databaseservice.converter.DocumentConverter;
import ru.kazimir.bortnik.jb.databaseservice.model.DocumentDTO;

@Service("documentService")
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final DocumentConverter documentConverter;

    @Autowired
    public DocumentServiceImpl(
            @Qualifier("documentRepository") DocumentRepository documentRepository,
            @Qualifier("documentConverter") DocumentConverter documentConverter
    ) {
        this.documentRepository = documentRepository;
        this.documentConverter = documentConverter;
    }

    @Override
    public DocumentDTO add(DocumentDTO documentDTO) {
        Document document = documentConverter.fromDTO(documentDTO);
        Document returnedDocument = documentRepository.add(document);
        return documentConverter.toDTO(returnedDocument);
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {

        Document returnedDocument = documentRepository.getDocumentById(id);
        return documentConverter.toDTO(returnedDocument);
    }

    @Override
    public void delete(Long id) {
        documentRepository.delete(id);
    }
}

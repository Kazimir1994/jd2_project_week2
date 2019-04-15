package ru.kazimir.bortnik.jb.databaserepository;

import ru.kazimir.bortnik.jb.databaserepository.model.Document;

public interface DocumentRepository {

    Document add(Document document);

    Document getDocumentById(Long id);

    void delete(Long id);
}

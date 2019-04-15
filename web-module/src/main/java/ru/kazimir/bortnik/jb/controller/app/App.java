package ru.kazimir.bortnik.jb.controller.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kazimir.bortnik.jb.controller.DocumentController;
import ru.kazimir.bortnik.jb.controller.config.AppConfig;
import ru.kazimir.bortnik.jb.databaseservice.model.DocumentDTO;

import java.util.UUID;

public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();

        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("description");
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());

        DocumentController documentController = context.getBean(DocumentController.class);

        DocumentDTO returnedDocumentDTO = documentController.add(documentDTO);
        DocumentDTO foundDocumentDTO = documentController.getDocumentById(returnedDocumentDTO.getId());
        documentController.delete(foundDocumentDTO.getId());
    }
}

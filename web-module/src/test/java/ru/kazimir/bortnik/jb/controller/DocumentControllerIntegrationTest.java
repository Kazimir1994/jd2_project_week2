package ru.kazimir.bortnik.jb.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.kazimir.bortnik.jb.controller.config.AppConfig;
import ru.kazimir.bortnik.jb.controller.exceptions.NullDocumentDTOException;
import ru.kazimir.bortnik.jb.controller.exceptions.NullDocumentDTOIDException;
import ru.kazimir.bortnik.jb.controller.exceptions.NullUniqueNumberException;
import ru.kazimir.bortnik.jb.databaseservice.model.DocumentDTO;

import java.util.UUID;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class)
public class DocumentControllerIntegrationTest {
    @Autowired
    @Qualifier("documentController")
    DocumentController documentController;

    @Test
    public void shouldSaveDocumentAndReturnEqualsDocumentDTOWithID() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("Description");
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        DocumentDTO returnedDocumentDTO = documentController.add(documentDTO);
        Assert.assertNotNull(returnedDocumentDTO.getId());
        Assert.assertEquals(documentDTO.getUniqueNumber(), returnedDocumentDTO.getUniqueNumber());
        Assert.assertEquals(documentDTO.getDescription(), returnedDocumentDTO.getDescription());
    }

    @Test
    public void shouldSaveTheDocumentAndReturnTheSameDocumentThatTheySaved() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("Description");
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        DocumentDTO returnedDocumentDTO = documentController.add(documentDTO);
        DocumentDTO returnedDocumentDTOById = documentController.getDocumentById(returnedDocumentDTO.getId());

        Assert.assertEquals(documentDTO.getUniqueNumber(), returnedDocumentDTOById.getUniqueNumber());
        Assert.assertEquals(documentDTO.getDescription(), returnedDocumentDTOById.getDescription());
    }

    @Test(expected = NullDocumentDTOIDException.class)
    public void shouldThrowAnError() {
        documentController.getDocumentById(443L);
    }

    @Test(expected = NullDocumentDTOIDException.class)
    public void shouldThrowAnErrorIfThereIsNoDocumentWithSuchId() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("Description");
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        DocumentDTO returnedDocumentDTO = documentController.add(documentDTO);
        documentController.delete(returnedDocumentDTO.getId());
        documentController.getDocumentById(returnedDocumentDTO.getId());
    }
}

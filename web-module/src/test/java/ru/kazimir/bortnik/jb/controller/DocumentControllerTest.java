package ru.kazimir.bortnik.jb.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.kazimir.bortnik.jb.databaseservice.DocumentService;
import ru.kazimir.bortnik.jb.databaseservice.model.DocumentDTO;
import ru.kazimir.bortnik.jb.controller.exceptions.*;
import ru.kazimir.bortnik.jb.controller.impl.DocumentControllerImpl;
import ru.kazimir.bortnik.jb.controller.validations.ValidationDocumentController;
import ru.kazimir.bortnik.jb.controller.validations.constants.KeyMessageError;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {
    @Mock
    private DocumentService documentService;
    @Mock
    private ValidationDocumentController validationDocumentController;

    private DocumentController documentController;

    @Before
    public void init() {
        documentController = new DocumentControllerImpl(documentService, validationDocumentController);

    }

    @Test
    public void shouldReturnADTODocumentWithTheIdField() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setDescription("DESCRIPTION");

        DocumentDTO returnDDTO = new DocumentDTO();
        returnDDTO.setId(1010L);

        Mockito.when(documentService.add(documentDTO)).thenReturn(returnDDTO);
        long returnDocumentID = documentController.add(documentDTO).getId();
        Assert.assertEquals(1010L, returnDocumentID);

    }

    @Test(expected = EmptyDocumentDTOArgumentException.class)
    public void shouldThrowAnErrorIfTheReturnedDocumentHasNoId() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setDescription("DESCRIPTION");

        Mockito.when(documentService.add(documentDTO)).thenReturn(documentDTO);

        Map<String, String> messagesError = new HashMap<>();
        messagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID, "Document id is missing");
        Mockito.when(validationDocumentController.validateReturningDocumentDTO(documentDTO)).thenReturn(messagesError);

        documentController.add(documentDTO);
    }

    @Test(expected = EmptyDocumentDTOArgumentException.class)
    public void shouldThrowAnErrorIfTheReturnedDocumentHasNoDescription() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setDescription("DESCRIPTION");

        DocumentDTO returnDDTO = new DocumentDTO();

        Mockito.when(documentService.add(documentDTO)).thenReturn(returnDDTO);

        Map<String, String> messagesError = new HashMap<>();
        messagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DESCRIPTION, "Document description is missing");
        Mockito.when(validationDocumentController.validateReturningDocumentDTO(returnDDTO)).thenReturn(messagesError);

        documentController.add(documentDTO);
    }

    @Test(expected = EmptyDocumentDTOArgumentException.class)
    public void shouldThrowAnErrorIfTheReturnedDocumentHasNoUniqueNumber() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setDescription("DESCRIPTION");

        DocumentDTO returnDDTO = new DocumentDTO();
        Mockito.when(documentService.add(documentDTO)).thenReturn(returnDDTO);

        Map<String, String> messagesError = new HashMap<>();
        messagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_UNIQUE_NUMBER, "Document unique number is missing");
        Mockito.when(validationDocumentController.validateReturningDocumentDTO(returnDDTO)).thenReturn(messagesError);

        documentController.add(documentDTO);
    }

    @Test(expected = NullDocumentDTOException.class)
    public void shouldGiveAnErrorIfTheInputIsEmptyDocument() {
        Map<String, String> messagesError = new HashMap<>();
        messagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DOCUMENT, "Document does not exist");
        Mockito.when(validationDocumentController.validateArgumentDocumentDTO(null)).thenReturn(messagesError);
        documentController.add(null);
    }

    @Test(expected = NullUniqueNumberException.class)
    public void shouldGiveAnErrorIfTheUniqueNumberIsEmpty() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("DESCRIPTION");

        Map<String, String> messagesError = new HashMap<>();
        messagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_UNIQUE_NUMBER, "Document unique number is missing");
        Mockito.when(validationDocumentController.validateArgumentDocumentDTO(documentDTO)).thenReturn(messagesError);
        documentController.add(documentDTO);
    }

    @Test(expected = InvalidDocumentDTODescriptionException.class)
    public void shouldGiveAnErrorIfTheDescriptionIsGreaterThanTheSpecifiedValue() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("reiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiffffffffffffffffff" +
                "dfffffffffffffffffffffffffffffffffffffff" +
                "dffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "dffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "dfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "dffffffffffffffffffffffffffffffffffffffffffffffffffffffffddiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiterterter");
        Map<String, String> messagesError = new HashMap<>();
        messagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_DESCRIPTION_IS_VERY_LARGE, "The document description is very large");

        Mockito.when(validationDocumentController.validateArgumentDocumentDTO(documentDTO)).thenReturn(messagesError);
        documentController.add(documentDTO);
    }

    @Test(expected = InvalidDocumentDTOUniqueNumberException.class)
    public void shouldGiveAnErrorIfTheUniqueNumberDoesNotMatchThePattern() {
        DocumentDTO documentDTO = new DocumentDTO();
        Map<String, String> messagesError = new HashMap<>();
        messagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_INVALID_DOCUMENT_UNIQUE_NUMBER, "Unique document number does not match the pattern");

        Mockito.when(validationDocumentController.validateArgumentDocumentDTO(documentDTO)).thenReturn(messagesError);
        documentController.add(documentDTO);
    }

    @Test(expected = NullDocumentDTOIDException.class)
    public void shouldGiveAnErrorIfYouBetrayANonExistentIdDocument() {
        Map<String, String> messagesError = new HashMap<>();
        messagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID, "Document id is missing");

        Mockito.when(validationDocumentController.validateID(null)).thenReturn(messagesError);
        documentController.delete(null);
    }


    @Test(expected = NullDocumentDTOIDException.class)
    public void shouldThrowErrorWhenRequestedWithEmptyId() {
        Map<String, String> messagesError = new HashMap<>();
        messagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID, "Document id is missing");

        Mockito.when(validationDocumentController.validateID(null)).thenReturn(messagesError);
        documentController.delete(null);
    }

    @Test
    public void shouldReturnDocumentDTOWithSameIDForGetDocumentByIDMethod() {
        DocumentDTO returningDTO = new DocumentDTO();
        returningDTO.setUniqueNumber(UUID.randomUUID().toString());
        returningDTO.setDescription("Description");
        returningDTO.setId(1L);
        Mockito.when(documentService.getDocumentById(1L)).thenReturn(returningDTO);
        long returnedID = documentController.getDocumentById(1L).getId();
        Assert.assertEquals(1L, returnedID);
    }


    @Test(expected = NullDocumentDTOException.class)
    public void shouldThrowNullReturningDocumentDTOExceptionIfServiceMethodReturnsNullAfterGetDocumentByIDMethod() {
        Mockito.when(documentService.getDocumentById(1L)).thenReturn(null);
        Map<String, String> messagesError = new HashMap<>();
        messagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DOCUMENT, "Document does not exist");
        Mockito.when(validationDocumentController.validateReturningDocumentDTO(null)).thenReturn(messagesError);
        documentController.getDocumentById(1L);
    }

}

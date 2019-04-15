package ru.kazimir.bortnik.jb.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.kazimir.bortnik.jb.controller.DocumentController;
import ru.kazimir.bortnik.jb.controller.exceptions.*;
import ru.kazimir.bortnik.jb.controller.validations.ValidationDocumentController;
import ru.kazimir.bortnik.jb.controller.validations.constants.KeyMessageError;
import ru.kazimir.bortnik.jb.databaseservice.DocumentService;
import ru.kazimir.bortnik.jb.databaseservice.model.DocumentDTO;

import java.util.Map;

@Controller("documentController")
public class DocumentControllerImpl implements DocumentController {
    private static final Logger logger = LogManager.getLogger(DocumentControllerImpl.class);
    private DocumentService documentService;
    private ValidationDocumentController validationDocumentController;

    @Autowired
    public DocumentControllerImpl(
            @Qualifier("documentService") DocumentService documentService,
            @Qualifier("validationDocumentController") ValidationDocumentController validationDocumentController) {
        this.documentService = documentService;
        this.validationDocumentController = validationDocumentController;

    }

    @Override
    public DocumentDTO add(DocumentDTO documentDTO) {
        Map<String, String> inputMessagesError = validationDocumentController.validateArgumentDocumentDTO(documentDTO);
        inputProcessingErrors(inputMessagesError, documentDTO);

        DocumentDTO returnDocumentDTO = documentService.add(documentDTO);

        Map<String, String> outputMessagesError = validationDocumentController.validateReturningDocumentDTO(returnDocumentDTO);
        outputProcessingErrors(outputMessagesError, documentDTO, returnDocumentDTO);

        return returnDocumentDTO;
    }

    private void outputProcessingErrors(Map<String, String> messagesError, DocumentDTO documentDTO, DocumentDTO returnDocumentDTO) {
        String formatMessage = "Output document={%s, %s, %s}\n Output document={%s, %s, %s}\n-> ERROR_TYPE:=(%s)";
        if (messagesError.containsKey(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID)) {
            logger.error(String.format(formatMessage,
                    documentDTO.getUniqueNumber(), documentDTO.getDescription(), documentDTO.getId(),
                    returnDocumentDTO.getUniqueNumber(), returnDocumentDTO.getDescription(), returnDocumentDTO.getId(),
                    messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID)));
            throw new EmptyDocumentDTOArgumentException(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID));
        }
        if (messagesError.containsKey(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DESCRIPTION)) {
            logger.error(String.format(formatMessage,
                    documentDTO.getUniqueNumber(), documentDTO.getDescription(), documentDTO.getId(),
                    returnDocumentDTO.getUniqueNumber(), returnDocumentDTO.getDescription(), returnDocumentDTO.getId(),
                    messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DESCRIPTION)));
            throw new EmptyDocumentDTOArgumentException(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DESCRIPTION));
        }
        if (messagesError.containsKey(KeyMessageError.KEY_MESSAGES_ERROR_NULL_UNIQUE_NUMBER)) {
            logger.error(String.format(formatMessage,
                    documentDTO.getUniqueNumber(), documentDTO.getDescription(), documentDTO.getId(),
                    returnDocumentDTO.getUniqueNumber(), returnDocumentDTO.getDescription(), returnDocumentDTO.getId(),
                    messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_UNIQUE_NUMBER)));
            throw new EmptyDocumentDTOArgumentException(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_UNIQUE_NUMBER));
        }
    }



    private void inputProcessingErrors(Map<String, String> messagesError, DocumentDTO documentDTO) {
        String formatMessage = "Input document={%s, %s, %s}-> ERROR_TYPE:=(%s)";
        if (messagesError.containsKey(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DOCUMENT)) {
            logger.error(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DOCUMENT));
            throw new NullDocumentDTOException(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DOCUMENT));
        } else {
            if (messagesError.containsKey(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID)) {
                logger.error(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID));
                throw new NullDocumentDTOIDException(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID));
            }
            if (messagesError.containsKey(KeyMessageError.KEY_MESSAGES_ERROR_NULL_UNIQUE_NUMBER)) {
                logger.error(String.format(formatMessage,
                        documentDTO.getUniqueNumber(), documentDTO.getDescription(), documentDTO.getId(),
                        messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_UNIQUE_NUMBER)));
                throw new NullUniqueNumberException(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_UNIQUE_NUMBER));
            } else if (messagesError.containsKey(KeyMessageError.KEY_MESSAGES_ERROR_INVALID_DOCUMENT_UNIQUE_NUMBER)) {
                logger.error(String.format(formatMessage,
                        documentDTO.getUniqueNumber(), documentDTO.getDescription(), documentDTO.getId(),
                        messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_INVALID_DOCUMENT_UNIQUE_NUMBER)));
                throw new InvalidDocumentDTOUniqueNumberException(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_INVALID_DOCUMENT_UNIQUE_NUMBER));
            }

            if (messagesError.containsKey(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DESCRIPTION)) {
                logger.error(String.format(formatMessage,
                        documentDTO.getUniqueNumber(), documentDTO.getDescription(), documentDTO.getId(),
                        messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DESCRIPTION)));
                throw new EmptyDocumentDTOArgumentException(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DESCRIPTION));
            } else if (messagesError.containsKey(KeyMessageError.KEY_MESSAGES_ERROR_DESCRIPTION_IS_VERY_LARGE)) {
                logger.error(String.format(formatMessage,
                        documentDTO.getUniqueNumber(), documentDTO.getDescription(), documentDTO.getId(),
                        messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_DESCRIPTION_IS_VERY_LARGE)));
                throw new InvalidDocumentDTODescriptionException(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_DESCRIPTION_IS_VERY_LARGE));
            }
        }
    }

    private void inputProcessingErrors(Map<String, String> messagesError) {
        if (messagesError.containsKey(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID)) {
            logger.error(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID));
            throw new NullDocumentDTOIDException(messagesError.get(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID));
        }
    }


    @Override
    public DocumentDTO getDocumentById(Long id) {
        Map<String, String> inputMessagesError = validationDocumentController.validateID(id);
        inputProcessingErrors(inputMessagesError);
        DocumentDTO documentDTO = documentService.getDocumentById(id);
        Map<String, String> outputMessagesError = validationDocumentController.validateReturningDocumentDTO(documentDTO);
        inputProcessingErrors(outputMessagesError, documentDTO);
        return documentDTO;
    }

    @Override
    public void delete(Long id) {
        Map<String, String> inputMessagesError = validationDocumentController.validateID(id);
        inputProcessingErrors(inputMessagesError);
        documentService.delete(id);
    }
}

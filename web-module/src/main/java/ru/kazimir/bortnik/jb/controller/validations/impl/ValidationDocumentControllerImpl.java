package ru.kazimir.bortnik.jb.controller.validations.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kazimir.bortnik.jb.controller.validations.ValidationDocumentController;
import ru.kazimir.bortnik.jb.controller.validations.constants.KeyMessageError;
import ru.kazimir.bortnik.jb.databaseservice.model.DocumentDTO;

import java.util.HashMap;
import java.util.Map;

@Component("validationDocumentController")
public class ValidationDocumentControllerImpl implements ValidationDocumentController {

    @Value("${messages.error.null.id}")
    private String MESSAGES_ERROR_NULL_ID;

    @Value("${messages.error.null.description}")
    private String MESSAGES_ERROR_NULL_DESCRIPTION;

    @Value("${messages.error.null.unique.Number}")
    private String MESSAGES_ERROR_UNIQUE_NUMBER;

    @Value("${messages.error.null.document}")
    private String MESSAGES_ERROR_NULL_DOCUMENT;


    @Value("${messages.error.description.is.very.large}")
    private String KEY_MESSAGES_ERROR_DESCRIPTION_IS_VERY_LARGE;

    private final static int MAXIMUM_DOCUMENT_DESCRIPTION_LENGTH = 100;
    private static final String UNIQUE_NUMBER_VALIDATION_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-" +
            "[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    @Value("${messages.error.Invalid.document.uniqueNumber}")
    private String KEY_MESSAGES_ERROR_INVALID_DOCUMENT_UNIQUE_NUMBER;

    @Override
    public Map<String, String> validateReturningDocumentDTO(DocumentDTO returningDocumentDTO) {
        Map<String, String> MessagesError = new HashMap<>();
        if (returningDocumentDTO == null) {
            MessagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DOCUMENT, MESSAGES_ERROR_NULL_DOCUMENT);
        } else {
            if (returningDocumentDTO.getId() == null) {
                MessagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID, MESSAGES_ERROR_NULL_ID);
            }

            if (returningDocumentDTO.getDescription() == null) {
                MessagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DESCRIPTION, MESSAGES_ERROR_NULL_DESCRIPTION);
            }
            if (returningDocumentDTO.getUniqueNumber() == null) {
                MessagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_UNIQUE_NUMBER, MESSAGES_ERROR_UNIQUE_NUMBER);
            }
        }
        return MessagesError;
    }


    @Override
    public Map<String, String> validateArgumentDocumentDTO(DocumentDTO incomingDocumentDTO) {
        Map<String, String> MessagesError = new HashMap<>();
        if (incomingDocumentDTO == null) {
            MessagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DOCUMENT, MESSAGES_ERROR_NULL_DOCUMENT);
        } else {
            if (incomingDocumentDTO.getDescription() == null) {
                MessagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_DESCRIPTION, MESSAGES_ERROR_NULL_DESCRIPTION);
            } else if (incomingDocumentDTO.getDescription().length() > MAXIMUM_DOCUMENT_DESCRIPTION_LENGTH) {
                MessagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_DESCRIPTION_IS_VERY_LARGE, KEY_MESSAGES_ERROR_DESCRIPTION_IS_VERY_LARGE);
            }

            if (incomingDocumentDTO.getUniqueNumber() == null) {
                MessagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_UNIQUE_NUMBER, MESSAGES_ERROR_UNIQUE_NUMBER);
            } else if (!incomingDocumentDTO.getUniqueNumber().matches(UNIQUE_NUMBER_VALIDATION_REGEX)) {
                MessagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_INVALID_DOCUMENT_UNIQUE_NUMBER, KEY_MESSAGES_ERROR_INVALID_DOCUMENT_UNIQUE_NUMBER);
            }

        }

        return MessagesError;
    }

    @Override
    public Map<String, String> validateID(Long id) {
        Map<String, String> MessagesError = new HashMap<>();
        if (id == null) {
            MessagesError.put(KeyMessageError.KEY_MESSAGES_ERROR_NULL_ID, MESSAGES_ERROR_NULL_ID);
        }
        return MessagesError;
    }


}

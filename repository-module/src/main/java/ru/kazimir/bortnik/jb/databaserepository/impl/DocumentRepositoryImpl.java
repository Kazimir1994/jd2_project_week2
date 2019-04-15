package ru.kazimir.bortnik.jb.databaserepository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.kazimir.bortnik.jb.databaserepository.DocumentRepository;
import ru.kazimir.bortnik.jb.databaserepository.connection.ConnectionDataBseService;
import ru.kazimir.bortnik.jb.databaserepository.model.Document;

import java.sql.*;

@Repository("documentRepository")
public class DocumentRepositoryImpl implements DocumentRepository {

    private static final Logger logger = LogManager.getLogger(DocumentRepositoryImpl.class);
    private final ConnectionDataBseService connectionHandler;

    @Autowired
    public DocumentRepositoryImpl(
            @Qualifier("connectionDataBseService") ConnectionDataBseService connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public Document add(Document document) {

        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(QueryTypes.ADD.QUERY, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, document.getUniqueNumber());
                statement.setString(2, document.getDescription());
                statement.execute();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    connection.commit();
                    logger.info(String.format("Add document {%s,%s}",
                            document.getDescription(),
                            document.getUniqueNumber()));
                    return getDocumentID(resultSet, document);
                }
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Document getDocumentID(ResultSet resultSet, Document document) {
        try {
            if (resultSet.next()) {
                document.setId(resultSet.getLong(1));
            }
            return document;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Document getDocumentById(Long id) {
        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(QueryTypes.GET_BY_ID.QUERY)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    connection.commit();
                    return buildingDocument(resultSet);
                }
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Document buildingDocument(ResultSet resultSet) {
        Document document = new Document();
        try {
            if (resultSet.next()) {
                document.setId(resultSet.getLong("id"));
                document.setUniqueNumber(resultSet.getString("unique_number"));
                document.setDescription(resultSet.getString("description"));
            }
            logger.info(String.format("Document received {%s,%s,%s}",
                    document.getId(),
                    document.getDescription(),
                    document.getUniqueNumber()));
            return document;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(QueryTypes.DELETE_BY_ID.QUERY)) {
                statement.setLong(1, id);
                statement.executeUpdate();
                connection.commit();
                logger.info("I deleted a document with id = " + id);
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    private enum QueryTypes {
        DELETE_BY_ID("UPDATE Document SET deleted = TRUE WHERE id = ?"),
        GET_BY_ID("SELECT id, unique_number, description FROM Document WHERE id = ? AND deleted = FALSE"),
        ADD("INSERT INTO Document(unique_number, description) VALUES (?, ?)");

        public String QUERY;

        QueryTypes(String QUERY) {
            this.QUERY = QUERY;
        }

    }

}

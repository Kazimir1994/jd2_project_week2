package ru.kazimir.bortnik.jb.databaserepository.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("databaseProperties")
public class DatabaseProperties {

    @Value("${database.url}")
    private String databaseURL;

    @Value("${database.driver.name}")
    private String databaseDriverName;

    @Value("${database.password}")
    private String databasePassword;

    @Value("${database.username}")
    private String databaseUsername;

    @Value("${database.config.name}")
    private String databaseConfigName;


    public String getDatabaseURL() {
        return databaseURL;
    }

    public String getDatabaseDriverName() {
        return databaseDriverName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabaseConfigName() {
        return databaseConfigName;
    }
}

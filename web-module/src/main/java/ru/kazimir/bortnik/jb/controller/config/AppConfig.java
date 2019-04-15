package ru.kazimir.bortnik.jb.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:configDatabase.properties")
@PropertySource("classpath:errorMessagesValidationDocument.properties")
@ComponentScan(basePackages = {
        "ru.kazimir.bortnik.jb.databaserepository",
        "ru.kazimir.bortnik.jb.databaseservice",
        "ru.kazimir.bortnik.jb.controller"
})
@EnableAspectJAutoProxy
public class AppConfig {
}

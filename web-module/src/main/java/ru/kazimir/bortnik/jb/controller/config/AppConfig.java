package ru.kazimir.bortnik.jb.controller.config;


import org.springframework.context.annotation.*;

@Configuration


@PropertySources({
        @PropertySource("classpath:configDatabase.properties"),
        @PropertySource("classpath:errorMessages.properties")
})
@ComponentScan(basePackages = {
        "ru.kazimir.bortnik.jb.databaserepository",
        "ru.kazimir.bortnik.jb.databaseservice",
        "ru.kazimir.bortnik.jb.controller"
})
@EnableAspectJAutoProxy
public class AppConfig {
}

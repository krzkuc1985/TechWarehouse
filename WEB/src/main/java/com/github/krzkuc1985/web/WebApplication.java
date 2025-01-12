package com.github.krzkuc1985.web;

import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.component.page.AppShellConfigurator;

@SpringBootApplication
@Theme(value = "web")
public class WebApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}

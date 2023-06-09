package com.assignment.cardealer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class CarDealerAPIConfig {

    @Bean
    public OpenAPI carDealerAPI() {

        Contact contact = new Contact();
        contact.setEmail("foromoernestteoro@gmail.com");
        contact.setName("Foromo Ernest TEORO");

        Info info = new Info()
                .title("Car dealer API Management service")
                .version("0.0.1")
                .contact(contact)
                .description("This API exposes API for managing car dearlers' listings");

        return new OpenAPI().info(info);
    }
}
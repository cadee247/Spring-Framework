package org.example.project.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Configuration class for customizing the OpenAPI/Swagger documentation.
 * This class sets metadata such as title, version, contact info, license,
 * and server URL that will appear in the Swagger UI.
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Creates and configures the OpenAPI bean.
     * Spring Boot automatically picks this up to generate Swagger documentation.
     *
     * @return a custom OpenAPI instance with API metadata.
     */
    @Bean
    public OpenAPI customOpenAPI() {

        // Define a server for Swagger UI (where your API runs)
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080"); // Base URL for your API
        localServer.setDescription("Local development server");

        // Contact information shown in Swagger UI
        Contact contact = new Contact();
        contact.setName("Your Name");
        contact.setEmail("youremail@example.com");

        // License information for the API
        License mit = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        // General API metadata: title, version, description, contact, license
        Info info = new Info()
                .title("Article Management API") // API name
                .version("1.0")                  // API version
                .contact(contact)                // Contact details
                .description("This API manages articles") // Description shown in Swagger
                .license(mit);                   // License info

        // Return the customized OpenAPI object
        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}

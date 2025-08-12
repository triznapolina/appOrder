package org.example.apporders.config;





import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Orders API")
                        .version("1.0")
                        .description("Orders API")
                .contact(new Contact()
                        .name("Polina Tr")
                        .email("polnatrizna8@gmail.com")));
    }
}

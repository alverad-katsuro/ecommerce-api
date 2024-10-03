package br.com.alverad.ecommerce.commons;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.alverad.ecommerce.application.config.swagger.SwaggerConfig;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@ApplicationModuleTest
public class CommonsIntegrationTest {


    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private SwaggerConfig swaggerConfig;

    @MockBean
    private ObjectMapperProvider objectMapperProvider;

    @MockBean
    private SpringDocConfiguration springDocConfiguration;

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;


    @Container
    @SuppressWarnings("resource")
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:16.0").withDatabaseName("integration-tests-db")
                    .withUsername("username").withPassword("password");
    // .withInitScript("test-data.sql");

    static {
        postgreSQLContainer.start();
    }


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void bootstrapsInventoryModule() {

    }
}

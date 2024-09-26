package br.com.alverad.ecommerce.application.config.swagger;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.alverad.ecommerce.commons.annotations.RotaPublica;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
@OpenAPIDefinition(info = @Info(title = "hodpe-api", version = "1.0",
        license = @License(name = "Defensoria Pública do Estado Pará",
                url = "http://defensoria.pa.def.br/missao.aspx"),
        description = "API do Sistema de Honorários da Defensoria Pública do Estado do Pará.",
        contact = @Contact(name = "Sistemas DPE-PA")),
        externalDocs = @ExternalDocumentation(description = "Wiki do Projeto no Gitlab",
                url = "https://gitlab.defensoria.pa.def.br/honorarios/hodpe-api/-/wikis/home"))

@SecurityScheme(name = "Bearer", type = SecuritySchemeType.HTTP, scheme = "bearer",
        bearerFormat = "JWT")
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("public").pathsToMatch("/**")
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent(RotaPublica.class))
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder().group("admin").pathsToMatch("/**")
                .addOpenApiCustomizer(externalGroupOpenApiCustomizer()).build();
    }

    OpenApiCustomizer externalGroupOpenApiCustomizer() {
        SecurityRequirement a = new SecurityRequirement();
        a.addList("Bearer");
        return openApi -> openApi.addSecurityItem(a);
    }

    // @Bean
    // public OperationCustomizer operationIdCustomizer() {
    // return (operation, handlerMethod) -> {
    // Class<?> superClazz = handlerMethod.getBeanType().getSuperclass();
    // try {
    // if (Objects.nonNull(superClazz)) {
    // OperationId annontation =
    // handlerMethod.getMethod().getAnnotation(OperationId.class);
    // if (operation != null) {
    // String beanName = handlerMethod.getBeanType().getSimpleName();
    // if (beanName.equals("CardKanbanController")) {
    // System.out.println(handlerMethod.getMethod().getName());
    // }
    // operation.setOperationId(Optional.of(annontation.value())
    // .orElse(handlerMethod.getMethod().getName()));
    // } else {
    // operation.setOperationId(handlerMethod.getMethod().getName());
    // }
    // }
    // return operation;

    // } catch (Exception e) {
    // // TODO: handle exception

    // return null;
    // }
    // };
    // }
}

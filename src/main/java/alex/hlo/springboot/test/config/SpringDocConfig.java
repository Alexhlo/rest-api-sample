package alex.hlo.springboot.test.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public GroupedOpenApi publicPostgresStudentApi() {
        return GroupedOpenApi.builder()
                .group("postgres-student")
                .pathsToMatch("/postgres/**")
                .build();
    }

    @Bean
    public OpenAPI springDocOpenApiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Postgres Student API")
                        .description("REST API example")
                        .version("v1"));
    }

}

package com.self.medstore.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI medStoreOpenAPI() {
		return new OpenAPI().info(new Info().title("Online Medicine Store API")
				.description("REST APIs for Medicine ordering, Admin & Customer features").version("1.0.0"));
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("medstore").pathsToMatch("/**").build();
	}
}

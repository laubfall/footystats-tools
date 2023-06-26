package de.footystats.tools.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.servers.ServerVariable;
import io.swagger.v3.oas.models.servers.ServerVariables;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Used to add a custom server specification to the openapi specification that can be parameterized.
 *
 * Parameter for openapi-gnerator-cli e.g.: --server-variables=env=ubuntu,port=3000
 */
@Component
@Profile("openapi")
public class OpenApiConfiguration {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().addServersItem(new Server().url("http://{env}:{port}").variables(new ServerVariables().addServerVariable("env", new ServerVariable()._default("localhost")).addServerVariable("port", new ServerVariable()._default("8080"))))
			.components(new Components().addSecuritySchemes("basicScheme",
				new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
	}
}

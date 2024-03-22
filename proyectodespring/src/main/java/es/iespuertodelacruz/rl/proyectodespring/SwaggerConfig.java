package es.iespuertodelacruz.rl.proyectodespring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {
	// private static final String PathSelectors = null;

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("es.iespuertodelacruz.rl.proyectodespring.controller"))
				.paths(PathSelectors.any()).build();
	}
	
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		registry
			.addResourceHandler("webjars/**")
			.addResourceLocations("classpath:META-INF/resources/webjars/");
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("Documentación con Swagger de la API").description("Detalles de la API REST de Incidencias")
				.version("1.0.0").build();
	}
}

package com.challenge.memoryapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// 
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.challenge.memoryapi.controllers"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	public Contact contact() {
		return new Contact("Vicente Simão", "http://github.com/vicente-jpro/memory-api",
				"vicente.simao.rails@gmail.com");
	}

	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Memory Challenge API.")
				.description("API do projecto de gerencia.")
				.version("1.0")
				.contact(contact())
				.build();
	}

}

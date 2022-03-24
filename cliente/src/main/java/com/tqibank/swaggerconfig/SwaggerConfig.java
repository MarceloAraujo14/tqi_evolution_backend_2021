package com.tqibank.swaggerconfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket apimcr(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tqibank.cliente"))
                .paths(regex("/api/*"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "MCR API REST",
                "API REST de controle de Reservas e Encomendas do MCR",
                "1.0",
                "Terms of Service",
                new Contact("Marcelo Araujo","https://www.tqibank.com.br","mbaraujo1406@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/license.html",new ArrayList<VendorExtension>());
        return apiInfo;
    }

}

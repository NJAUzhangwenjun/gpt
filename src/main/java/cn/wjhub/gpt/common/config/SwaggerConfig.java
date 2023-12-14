package cn.wjhub.gpt.common.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // @Bean
    // public Docket api() {
    //     return new Docket(DocumentationType.SWAGGER_2)
    //             .select()
    //             .apis(RequestHandlerSelectors.basePackage("cn.wjhub.gpt")) // 指定扫描的控制器包
    //             .paths(PathSelectors.any())
    //             .build()
    //             .apiInfo(new ApiInfoBuilder()
    //                     .title("Swagger API Documentation")
    //                     .description("API Documentation for your application")
    //                     .version("1.0.0")
    //                     .build());
    // }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("AI美妆小助手")
                        .description("AI美妆小助手API文档")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("项目地址")
                        .url("https://github.com/NJAUzhangwenjun/gpt"));
    }

}

package io.wisoft.iotplatform.device.configurer;

import com.google.common.base.Predicate;
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

import static com.google.common.base.Predicates.containsPattern;
import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("io.wisoft.iotplatform"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("NRF IotPlatform Device Api Documentation")
        .description("This documents describes about NRF IoTPlatform Device API.")
        .contact(new Contact("WiSoft Lab.",
            "http://www.wisoft.io/",
            "contact@wisoft.io"))
        .version("2019.30")
        .build();
  }

  @SuppressWarnings("unchecked")
  private Predicate<String> paths() {
    return or(containsPattern("/api/.*"));
  }

}

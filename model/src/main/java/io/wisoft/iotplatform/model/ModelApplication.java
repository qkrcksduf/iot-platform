package io.wisoft.iotplatform.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@EnableCircuitBreaker
@Slf4j
@SpringBootApplication
@SuppressWarnings("squid:S2095")
public class ModelApplication {

  public static void main(final String... args) {
    SpringApplication.run(ModelApplication.class, args);
  }

}

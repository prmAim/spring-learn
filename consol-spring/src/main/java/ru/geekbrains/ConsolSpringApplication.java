package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.dto.PaginatedResponse;
import ru.geekbrains.dto.ProductDto;

/**
 * Консольное приложение через spring
 */
@SpringBootApplication
public class ConsolSpringApplication implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(ConsolSpringApplication.class);

  /**
   * Запуск
   */
  public static void main(String[] args) {
    // old
    // SpringApplication.run(ConsolSpringApplication.class, args);
    // This is consol application
    SpringApplication application = new SpringApplicationBuilder(ConsolSpringApplication.class)
            .web(WebApplicationType.NONE)           // отключение WEB
            .build();
    application.run(args);
  }

  /**
   * Написание кода
   */
  @Override
  public void run(String... args) throws Exception {
    logger.info("The application is running!");
    String url = "http://localhost:8081/mvc-app/rest/v1/product/all";
    // The first method
    RestTemplate template = new RestTemplate();
    ResponseEntity<String> forEntity = template.getForEntity(url, String.class);
    ResponseEntity<PaginatedResponse<ProductDto>> resp = template.exchange(url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<PaginatedResponse<ProductDto>>() {});
    logger.info("The first method: ", forEntity.getBody());
    logger.info("The first 2 method: ", resp.getBody());

  }
}

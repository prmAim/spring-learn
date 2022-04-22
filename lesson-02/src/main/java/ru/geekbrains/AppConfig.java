package ru.geekbrains;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.geekbrains.product.ProductRepository;
import ru.geekbrains.product.ProductRepositoryImpl;

/**
 * @Configuration = Создание конфигурации Spring, в котором указываем, какие классы создать и обозначить их зависимости.
 * @ComponentScan = сканирование пакета ru.geekbrains и, если там прописаны аннотации @Repository, @Service, то автоматически
 * будут созданы классы и обозначены их зависимости.
 */
@Configuration
@ComponentScan("ru.geekbrains")
public class AppConfig {

  //  Создание независимого класса <Колеекция продуктов>. Фабричным методом @Bean. По умолчанию все компоненты Spring являются синглтонами
  //  объектом «одиночка» (Singleton)
  @Bean
  public ProductRepository productRepository() {
    return new ProductRepositoryImpl();
  }

  //  Создание независимого класса <Корзина>. Фабричным методом @Bean и @Scope("prototype") = (при создании НОВЫЙ экземпляр)
  @Bean
  @Scope("prototype")
  public CartService CartService() {
    return new CartService();
  }
}

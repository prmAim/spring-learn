package main.java.ru.geekbrains.caseWhenHandler.config;

import main.java.ru.geekbrains.caseWhenHandler.CartGoods;
import main.java.ru.geekbrains.caseWhenHandler.commands.NewCartCommand;
import main.java.ru.geekbrains.caseWhenHandler.product.ProductRepository;
import main.java.ru.geekbrains.caseWhenHandler.product.ProductRepositoryImpl;
import main.java.ru.geekbrains.screenserver.ColorFrame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.awt.*;
import java.util.Random;

/**
 * @Configuration = Создание конфигурации Spring, в котором указываем, какие классы создать и обозначить их зависимости.
 * @ComponentScan = сканирование пакета ru.geekbrains и, если там прописаны аннотации @Repository, @Service, то автоматически
 * будут созданы классы и обозначены их зависимости.
 */
@Configuration
@ComponentScan(basePackages = "main.java.ru.geekbrains.caseWhenHandler")
public class AppConfigCaseHandler {
    //  Создание независимого класса <Колеекция продуктов>. Фабричным методом @Bean. По умолчанию все компоненты Spring являются синглтонами
    //  объектом «одиночка» (Singleton)
    @Bean
    public ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }

    //  Создание независимого класса <Корзина>. Фабричным методом @Bean и @Scope("prototype") = (при создании НОВЫЙ экземпляр)
    @Bean
    @Scope("prototype")
    public CartGoods cartGoods() {
        return new CartGoods(){

        };
    }

    @Bean
    public NewCartCommand newCartCommand(){
        return new NewCartCommand() {
            @Override
            protected CartGoods getCartGoods() {
                // создаем bean = "cartGoods()" так как он prototype, то при каждом обращении будет создан новый bean CartGoods
                return cartGoods();
            }
        };
    }
}

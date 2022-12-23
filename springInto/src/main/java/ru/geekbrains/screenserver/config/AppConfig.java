package main.java.ru.geekbrains.screenserver.config;

import main.java.ru.geekbrains.caseWhenHandler.CartGoods;
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
@ComponentScan(basePackages = "main.java.ru.geekbrains.screenserver")
public class AppConfig {
    /**** Блок screenserver ***/
    @Bean
    @Scope("prototype")
    public Color color(){
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    @Bean
    public ColorFrame colorFrame(){
        return new ColorFrame() {
            @Override
            protected Color getColor() {
                // создаем bean = "color()" так как он prototype, то при каждом обращении будет создан новый bean  Color
                return color();
            }
        };
    }
}

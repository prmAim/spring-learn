package test.java.ru.geekbrains.screenserver;

import main.java.ru.geekbrains.screenserver.ColorFrame;
import main.java.ru.geekbrains.screenserver.config.Config;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Тест вызов frame(singleton) с постоянной сменой цвета(prototype)
 */
public class ColorFrameTest {
    private static ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        this.context = new AnnotationConfigApplicationContext(Config.class);
    }

    @Test
    public void showOnRandomPlaceTest() throws InterruptedException {
        while (true) {
            context.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(100);
        }
    }

}
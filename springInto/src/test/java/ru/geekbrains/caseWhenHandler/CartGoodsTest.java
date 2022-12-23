package test.java.ru.geekbrains.caseWhenHandler;

import main.java.ru.geekbrains.caseWhenHandler.commands.CommandHandler;
import main.java.ru.geekbrains.caseWhenHandler.config.AppConfigCaseHandler;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CartGoodsTest {

    private static ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        // Создание классов из Spring фабричным методом. Beans описаны в классе AppConfig
        context = new AnnotationConfigApplicationContext(AppConfigCaseHandler.class);
    }

    @Test
    public void cartGoodsTest() throws InterruptedException {
        // выполнение команд (реализованых ниже) через Spring (packet <commands>)
        context.getBean(CommandHandler.class).handleCommands();
    }
}
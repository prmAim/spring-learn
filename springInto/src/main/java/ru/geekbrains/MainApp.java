package main.java.ru.geekbrains;

import main.java.ru.geekbrains.caseWhenHandler.commands.CommandHandler;
import main.java.ru.geekbrains.caseWhenHandler.config.AppConfigCaseHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        // Создание классов из Spring фабричным методом. Beans описаны в классе AppConfig
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfigCaseHandler.class);
        context.getBean(CommandHandler.class).handleCommands();
    }
}

package main.java.ru.geekbrains.caseWhenHandler.commands;

import main.java.ru.geekbrains.caseWhenHandler.CartGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Обработка и ввод полученных команд
 */
@Service
public class CommandHandler {

    private final Map<String, Commands> commands;

    // автоматически ContextSpring нашел все реализации <Commands> и создал из них <Список>
    @Autowired
    public CommandHandler(List<Commands> commands) {
        // Преобразование команд в Map (<Название команд>, <Сама команда>). <Сама команда> пример: ADD, NEW ...
        this.commands = commands.stream()
                .collect(Collectors.toMap(Commands::getName, cmd -> cmd));
    }

    public void handleCommands() {
        Scanner scn = new Scanner(System.in);
        CartGoods cart = null;
        while (true) {
            System.out.println("Enter command: ");
            String cmd = scn.nextLine().trim().toUpperCase();
            if (cmd.equals("EXIT")) {
                System.out.println("Bye");
                return;
            }
            Commands command = commands.get(cmd);
            if (command == null) {
                System.out.println("Command unknown");
                continue;
            }
            cart = command.executeCommand(scn, cart);
        }
    }
}

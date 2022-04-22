package ru.geekbrains.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.CartService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Обработка и ввод полученных команд
 */
@Service
public class CommandHandler {

    // Список всех команд
    private final Map<String, Command> commands;

    @Autowired
    public CommandHandler(List<Command> commands) {
        // Преобразование команд в Map (<Название команд>, <Сама команда>). <Сама команда> пример: ADD, NEW ...
        this.commands = commands.stream()
                .collect(Collectors.toMap(Command::getName, cmd -> cmd));
    }

    public void handleCommands() {
        Scanner scn = new Scanner(System.in);
        CartService cartService = null;
        while (true) {
            System.out.println("Enter command: ");
            String cmd = scn.nextLine().trim().toUpperCase();
            if (cmd.equals("EXIT")) {
                System.out.println("Bye");
                return;
            }
            Command command = commands.get(cmd);
            if (command == null) {
                System.out.println("Command unknown");
                continue;
            }
            cartService = command.execute(scn, cartService);
        }
    }
}

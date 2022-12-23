package main.java.ru.geekbrains.caseWhenHandler.commands;

import main.java.ru.geekbrains.caseWhenHandler.CartGoods;

import java.util.Scanner;

public interface Commands {

    /**
     * Получить наименование команды.  Замены конструкции (case when).
     */
    String getName();

    /**
     * выполнение логики команды
     */
    CartGoods executeCommand(Scanner scan, CartGoods cartGoods);
}

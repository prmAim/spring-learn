package main.java.ru.geekbrains.caseWhenHandler.commands;

import main.java.ru.geekbrains.caseWhenHandler.CartGoods;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DeleteCommand implements Commands {

    @Override
    public String getName() {
        return "DELETE";
    }

    @Override
    public CartGoods executeCommand(Scanner scn, CartGoods cart) {
        System.out.println("Command is not fully implemented");
        return cart;
    }
}

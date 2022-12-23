package main.java.ru.geekbrains.caseWhenHandler.commands;

import main.java.ru.geekbrains.caseWhenHandler.CartGoods;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ShowCommand implements Commands {

    @Override
    public String getName() {
        return "SHOW";
    }

    @Override
    public CartGoods executeCommand(Scanner scn, CartGoods cart) {
        cart.getAll().forEach(System.out::println);
        return cart;
    }
}

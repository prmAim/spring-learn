package ru.geekbrains.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.AppConfig;
import ru.geekbrains.CartService;

import java.util.Scanner;

@Component
public class NewCartCommand implements Command {

    @Autowired
    private AppConfig appConfig;

    @Override
    public String getName() {
        return "NEW";
    }

    @Override
    public CartService execute(Scanner scn, CartService cart) {
        CartService cartService = appConfig.CartService();
        System.out.println("New cart created" + cartService);
        return cartService;
    }
}

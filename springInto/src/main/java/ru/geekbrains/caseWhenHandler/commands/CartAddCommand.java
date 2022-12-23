package main.java.ru.geekbrains.caseWhenHandler.commands;

import main.java.ru.geekbrains.caseWhenHandler.CartGoods;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Команда на добавление продукта в корзину
 */
@Component
public class CartAddCommand implements Commands {

    @Override
    public String getName() {
        return "ADD";
    }

    @Override
    public CartGoods executeCommand(Scanner scn, CartGoods cart) {
        if (cart == null) {
            System.out.println("Please create a new Cart");
            return cart;
        }
        System.out.print("Enter id: ");
        long id = scn.nextLong();
        System.out.print("Enter count: ");
        int count = scn.nextInt();
        cart.addProduct(id, count);
        return cart;
    }
}

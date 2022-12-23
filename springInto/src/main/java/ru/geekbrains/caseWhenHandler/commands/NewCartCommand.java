package main.java.ru.geekbrains.caseWhenHandler.commands;

import main.java.ru.geekbrains.caseWhenHandler.CartGoods;
import main.java.ru.geekbrains.caseWhenHandler.config.AppConfigCaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public abstract class NewCartCommand implements Commands {

    @Override
    public String getName() {
        return "NEW";
    }

    @Override
    public CartGoods executeCommand(Scanner scn, CartGoods cart) {
        CartGoods cartGoods = getCartGoods();
        System.out.println("New cart created" + cartGoods);
        return cartGoods;
    }

    protected abstract CartGoods getCartGoods();
}

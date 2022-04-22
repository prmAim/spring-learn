package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.product.Product;
import ru.geekbrains.product.ProductRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
  public static void main(String[] args) {
    // Создание классов из Spring фабричным методом. Beans описаны в классе AppConfig
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    ProductRepository allProduct = context.getBean("productRepository", ProductRepository.class);

    Cart cart = null;

    Scanner sc = new Scanner(System.in);
    while (true) {
      getMenu();
      int pressIn = Integer.parseInt(sc.nextLine());
      if (pressIn == 0) {
        break;
      }
      switch (pressIn) {
        //1 - Создать новую корзину;
        case 1:
          cart = context.getBean("сart", Cart.class);
          System.out.println(cart.toString());
          System.out.println("Корзина успешно создана");
          break;
        // 2 - Показать список продуктов в корзине
        case 2:
          if (cart == null) {
            System.out.println("Корзина еще не создана!");
          } else {
            cart.getProductAll();
          }
          break;
        // 3 - Добавить продукты в корзину
        case 3:
          // Выбираем корзину
          if (cart == null) {
            System.out.println("Корзина еще не создана!");
          } else {
            for (Product product : allProduct.fiandAll()) {
              System.out.printf("Номер продукта %3d %10s %4.2f \n", product.getId(), product.getTitle(), product.getCost());
            }
            cart.addProduct(chooseProduct(allProduct));
          }
          break;
        // 4 - Удалить продукты из корзины
        case 4:
          // Выбираем корзину
          if (cart == null) {
            System.out.println("Корзина еще не создана!");
          } else {
            cart.getProductAll();
            cart.removeProduct(chooseProduct(allProduct));
          }
          break;
        default:
          System.out.println("Укажите правильный номер!");
      }
    }

  }

  /**
   * Основное меню
   */
  private static void getMenu() {
    System.out.printf("Выбереите пункт меню: \n" +
            "1 - Создать новую корзину; \n" +
            "2 - Показать список продуктов в корзине \n" +
            "3 - Добавить продукты в корзину \n" +
            "4 - Удалить продукты из корзины \n");
  }

  /**
   * Выбрать продукты
   */
  private static int chooseProduct(ProductRepository allProduct) {
    System.out.print("Выбираем продукт = ");
    Scanner sc2 = new Scanner(System.in);
    int intIn = Integer.parseInt(sc2.nextLine());
    return (allProduct.findById(intIn) != null) ? intIn : 0;
  }
}


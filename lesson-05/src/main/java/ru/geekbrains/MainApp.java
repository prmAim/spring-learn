package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.model.Product;
import ru.geekbrains.model.ProductRepository;
import ru.geekbrains.model.ProductRepositoryDB;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.Scanner;

public class MainApp {
  public static void main(String[] args) {
    // Подключение к БД через JPA Hibernate, используя настройки подключения hibernate.cfg.xml
    // Получаем фабрику менеджеров сущностей
    EntityManagerFactory emFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    ProductRepository productRepository = new ProductRepositoryDB(emFactory);

    Scanner scn = new Scanner(System.in);
    while (true) {
      System.out.print("Enter command: ");
      String cmd = scn.nextLine().trim().toUpperCase();
      switch (cmd) {
        case "SHOW":
          productRepository.findAll();
          break;
        case "SHOW BY ID":
          System.out.print("Enter ID: ");
          long id = scn.nextLong();
          productRepository.findById(id);
          break;
        case "ADD OR UPDATE PRODUCT":
          System.out.print("Enter name: ");
          String name = scn.nextLine();
          System.out.print("Enter cost: ");
          BigDecimal cost = scn.nextBigDecimal();
          productRepository.save(new Product(name, cost));
          break;
        case "REMOVE PRODUCT":
          System.out.print("Enter ID: ");
          long id2 = scn.nextLong();
          productRepository.delete(id2);
          break;
        case "EXIT":
          emFactory.close();
          return;
      }
    }
  }
}

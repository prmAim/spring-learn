package ru.geekbrains;

import ru.geekbrains.product.Product;
import ru.geekbrains.product.ProductMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Реакция на событие
 */
@WebListener
public class BootstrapListener implements ServletContextListener {

  /**
   * Выполнение инициализации (во время запуском приложение)
   */
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ProductMap productMap = new ProductMap();
    productMap.insert(new Product("Виноград", 140.05f));
    productMap.insert(new Product("Бананы", 110.10f));
    productMap.insert(new Product("Киви", 60.0f));
    productMap.insert(new Product("Гранат", 240.0f));
    productMap.insert(new Product("Яблоки", 95.50f));
    productMap.insert(new Product("Авокадо", 50.95f));
    productMap.insert(new Product("Персики", 250.10f));
    productMap.insert(new Product("Арбуз", 100.0f));
    productMap.insert(new Product("Апельсин", 120.30f));
    productMap.insert(new Product("Мандарин", 145.40f));

    // Если нужен объект в рамках ВСЕГО приложения, то getContext доступем из всех точек
    sce.getServletContext().setAttribute("productMap", productMap);
  }
}

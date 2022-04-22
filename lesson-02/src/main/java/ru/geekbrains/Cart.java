package ru.geekbrains;

import ru.geekbrains.product.Product;
import ru.geekbrains.product.ProductRepository;

// Интерфейс <Корзина>
public interface Cart {
  /**
   * Порлучить список всех продуктов в корзине
   */
  void getProductAll();

  /**
   * Добавить продукт в корзину
   */
  void addProduct(long id);

  /**
   * Удалить продукт из корзины
   */
  void removeProduct(long id);
}

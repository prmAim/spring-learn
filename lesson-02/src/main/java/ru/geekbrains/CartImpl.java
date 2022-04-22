package ru.geekbrains;

import org.springframework.beans.factory.annotation.Autowired;
import ru.geekbrains.product.Product;
import ru.geekbrains.product.ProductRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс <корзина>
 */

public class CartImpl implements Cart {

  private ProductRepository productRepository;
  private Map<Product, Integer> mapCart;

  @Autowired
  public CartImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
    this.mapCart = new HashMap<>();
  }

  @Override
  public void getProductAll() {
    if (mapCart.isEmpty()) {
      System.out.println("Нет продуктов в корзине");
      return;
    }
    mapCart.forEach((key, value) ->System.out.printf("ИД: %3d %10s кол-во: %3d \n", key.getId(), key.getTitle(), value));
  }

  @Override
  public void addProduct(long id) {
    int countProduct = mapCart.getOrDefault(productRepository.findById(id), 0) + 1;
    mapCart.put(productRepository.findById(id), countProduct);
    System.out.println("Продукт успешно добавлен в корзину!");
  }

  @Override
  public void removeProduct(long id) {
    if (mapCart.getOrDefault(productRepository.findById(id), 0) <= 0) {
      System.out.println("Такого продукта в корзине нет!");
      return;
    }
    mapCart.put(productRepository.findById(id), mapCart.get(productRepository.findById(id)) - 1);
    System.out.println("Продуктов из корзины успешно изъяли!");
  }


}

package ru.geekbrains.product;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
  // потокобезопасный классы!!!
  private final Map<Long, Product> productMap = new HashMap<>();
  private final AtomicLong identity = new AtomicLong(0);

  // Инициализация после создания объекта
  @PostConstruct          // необходимо добавить зависимость в maven
  public void init(){
    this.insert(new Product("Виноград", 140.05f));
    this.insert(new Product("Бананы", 110.10f));
    this.insert(new Product("Киви", 60.0f));
    this.insert(new Product("Гранат", 240.0f));
    this.insert(new Product("Яблоки", 95.50f));
  }

  public List<Product> fiandAll() {
    return new ArrayList<>(productMap.values());
  }

  public Product findById(long id) {
    return productMap.get(id);
  }

  public void insert(Product product) {
    long id = identity.incrementAndGet();
    product.setId(id);
    productMap.put(id, product);
  }

  public void remove(Product product) {
    productMap.remove(product);
  }


}

package ru.geekbrains.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductMap {
  // потокобезопасный классы!!!
  private final Map<Long, Product> productMap = new ConcurrentHashMap<>();
  private final AtomicLong identity = new AtomicLong(0);

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
}

package ru.geekbrains.model;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepositoryImpl implements ProductRepository {

    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();

    private final AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {
        this.save(new Product(null, "Bananes", 10.20f));
        this.save(new Product(null, "Cherry", 20.50f));
        this.save(new Product(null, "Oranges", 40.60f));
        this.save(new Product(null, "Apple", 40.60f));
        this.save(new Product(null, "Tomato", 40.60f));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    @Override
    public Optional<Product> findById(long id) {
        return Optional.ofNullable(productMap.get(id));
    }

    @Override
    public void save(Product product) {
        if (product.getId() == null) {
            long id = identity.incrementAndGet();
            product.setId(id);
        }
        productMap.put(product.getId(), product);
    }

    @Override
    public void delete(long id) {
        productMap.remove(id);
    }
}

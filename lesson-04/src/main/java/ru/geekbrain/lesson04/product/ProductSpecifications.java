package ru.geekbrain.lesson04.product;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

// Для создния фильтра
public final class ProductSpecifications {

    /**
     * Больше  или равно чем minCost
     */
    public static Specification<Product> minCostContaining(BigDecimal minCost) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("cost"), minCost);
    }

    /**
     * Меньше или равно чем maxCost
     */
    public static Specification<Product> maxCostContaining(BigDecimal maxCost) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("cost"), maxCost);
    }
}

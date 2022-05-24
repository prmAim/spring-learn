package ru.geekbrain.lesson04.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Реализация интерфейса. DAO ProductRepository
 */
public interface ProductRepositoryDB extends JpaRepository<Product, Long> {
  @Query("select u " +
          " from Product u " +
          "where u.cost >= case when :minCost is not null" +
          "                     then :minCost" +
          "                     else u.cost" +
          "                 end  " +
          "  and u.cost <= case when :maxCost is not null" +
          "                     then :maxCost" +
          "                     else u.cost" +
          "                end")
  List<Product> findProductByCostBetween(@Param("minCost") BigDecimal minCost, @Param("maxCost") BigDecimal maxCost);
}

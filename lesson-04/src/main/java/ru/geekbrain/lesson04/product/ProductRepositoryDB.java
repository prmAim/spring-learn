package ru.geekbrain.lesson04.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Реализация интерфейса. DAO ProductRepository
 */
public interface ProductRepositoryDB extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
  @Query("select u " +
          " from Product u " +
          "where (u.cost >= :minCost or :minCost is null)" +
          "  and (u.cost <= :maxCost or :maxCost is null)")
  List<Product> findProductByCostBetween(@Param("minCost") BigDecimal minCost, @Param("maxCost") BigDecimal maxCost);
}

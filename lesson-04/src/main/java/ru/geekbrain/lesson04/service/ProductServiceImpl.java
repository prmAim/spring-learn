package ru.geekbrain.lesson04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrain.lesson04.dto.ProductDto;
import ru.geekbrain.lesson04.product.Product;
import ru.geekbrain.lesson04.product.ProductRepositoryDB;
import ru.geekbrain.lesson04.product.ProductSpecifications;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * На уровне сервиса должна быть организована бизнес-логика, транзакционность
 */
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepositoryDB productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepositoryDB productRepository) {
    this.productRepository = productRepository;
  }

  /**
   * Уже содержит преобразование в лист коллекции
   */
  @Override
  public Page<ProductDto> findProductsByFilter(BigDecimal minCost, BigDecimal maxCost, Integer page, Integer size, String sortCol) {
    Specification<Product> spec = Specification.where(null);
    if (minCost != null) {
      spec = spec.and(ProductSpecifications.minCostContaining(minCost));
    }
    if (maxCost != null) {
      spec = spec.and(ProductSpecifications.maxCostContaining(maxCost));
    }
    if (sortCol == null) {
      sortCol = "id";
    }
    // PageRequest.of(page, size, Sort.by("id"))) = реализация пагинации page = номер страницы, size = объем страницы
    // Sort.by - сортировка
    return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortCol)))
            .map(ProductServiceImpl::productToDto);
  }

  @Override
  public Optional<ProductDto> findById(long id) {
    return productRepository.findById(id).map(ProductServiceImpl::productToDto);
  }

  @Override
  public ProductDto save(ProductDto product) {
    return productToDto(productRepository.save(
            new Product(
                    product.getId(),
                    product.getName(),
                    product.getCost()
            )));
  }

  @Override
  public void deleteById(long id) {
    productRepository.deleteById(id);
  }

  /**
   * Преобразование из DAO в DTO
   */
  private static ProductDto productToDto(Product product) {
    return new ProductDto(product.getId(), product.getName(), product.getCost());
  }
}

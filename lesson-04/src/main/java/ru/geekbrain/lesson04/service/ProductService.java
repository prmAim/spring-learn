package ru.geekbrain.lesson04.service;

import org.springframework.data.domain.Page;
import ru.geekbrain.lesson04.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findProductsByFilter(BigDecimal minCost, BigDecimal maxCost, Integer page, Integer size, String sortCol);

    Optional<ProductDto> findById(long id);

    ProductDto save (ProductDto product);

    void deleteById(long id);
}

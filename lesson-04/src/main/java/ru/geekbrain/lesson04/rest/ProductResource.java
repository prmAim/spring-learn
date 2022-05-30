package ru.geekbrain.lesson04.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrain.lesson04.dto.ProductDto;
import ru.geekbrain.lesson04.exception.NotFoundException;
import ru.geekbrain.lesson04.service.ProductService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

@RequestMapping("/rest/v1/product")
@RestController
public class ProductResource {

  private final ProductService productService;

  /**
   * @Autowired - зависимость. создание Bean[объект] ProductRepository, который управляется spring
   */
  @Autowired
  public ProductResource(ProductService productService) {
    this.productService = productService;
  }


  //  Если это контроллер @Controller + @ResponseBody + @GetMapping, то на выходе на выходе не будет искать модель, а отправит как есть
  //  Eсли это контроллер @Controller + @GetMapping, то на выходе мы ищем название модели представления
  //  Если это контроллер @RestController + @GetMapping, то не требуется @ResponseBody
  @GetMapping("/all")
  public Page<ProductDto> findAll(
          @RequestParam Optional<BigDecimal> minCost,
          @RequestParam Optional<BigDecimal> maxCost,
          // пагинация page = № страницы, size = размер страницы
          @RequestParam Optional<Integer> page,
          @RequestParam Optional<Integer> size,
          @RequestParam Optional<String> sortCol) {
    Integer pageValue = page.orElse(1) - 1;
    Integer sizeValue = size.orElse(3);
    String sortColValue = sortCol.filter(s -> !s.isBlank()).orElse("id");
    return productService.findProductsByFilter(
            minCost.isPresent() ? minCost.get() : null,
            maxCost.isPresent() ? maxCost.get() : null,
            pageValue,
            sizeValue,
            sortColValue);
  }

  @GetMapping("/{id}/id")
  public ProductDto form(@PathVariable("id") long id) {
    return productService.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found!"));        // Если такого продукта нет, то усключение
  }

  // @RequestBody = получение объекта через тело запроса
  @PostMapping
  public ProductDto create(@RequestBody ProductDto product) {
    if (product.getId() != null){
      throw new IllegalArgumentException("This product was created!");
    }
    return productService.save(product);
  }

  // @RequestBody = получение объекта через тело запроса
  @PutMapping
  public ProductDto update(@RequestBody ProductDto product) {
    if (product.getId() == null){
      throw new IllegalArgumentException("This product was not created!");
    }
    return productService.save(product);
  }

  @DeleteMapping("/{id}")
  public void remove(@PathVariable("id") long id) {
    productService.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found!"));
    productService.deleteById(id);
  }

  /**
   * Если в процесе работы контроллера возникнет исключение NotFoundException, то будет перехват
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)   // Ошибка 404
  @ExceptionHandler
  public String notFoundExceptionHandler(NotFoundException e) {
    return e.getMessage();
  }

  /**
   * Если в процесе работы контроллера возникнет исключение IllegalArgumentException, то будет перехват
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)   // Ошибка 400
  @ExceptionHandler
  public String illegalArgumentException(IllegalArgumentException e) {
    return e.getMessage();
  }

  /**
   * Если в процесе работы контроллера возникнет исключение IllegalArgumentException, то будет перехват
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)   // Ошибка 500
  @ExceptionHandler
  public String sqlException(SQLException e) {
    return e.getMessage();
  }
}

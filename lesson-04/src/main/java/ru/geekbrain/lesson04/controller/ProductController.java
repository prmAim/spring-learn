package ru.geekbrain.lesson04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrain.lesson04.dto.ProductDto;
import ru.geekbrain.lesson04.exception.NotFoundException;
import ru.geekbrain.lesson04.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Создание контроллера обработки URL .../user через DispatcherServlet
 *
 * @Controller - должны быть эта аннатация
 */
@RequestMapping("/product")
@Controller
public class ProductController {

  private final ProductService productService;

  /**
   * @Autowired - зависимость. создание Bean[объект] ProductRepository, который управляется spring
   */
  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * Выбор продукта
   * Уровень контроллера. Обрабатываем метод GET URL  .../product/*
   */
  @GetMapping
  public String listPage(
          @RequestParam Optional<BigDecimal> minCost,
          @RequestParam Optional<BigDecimal> maxCost,
          // пагинация page = № страницы, size = размер страницы
          @RequestParam Optional<Integer> page,
          @RequestParam Optional<Integer> size,
          @RequestParam Optional<String> sortCol,
          Model model) {
    // Расчет идет с 0, а пользователь видит с № 1
    Integer pageValue = page.orElse(1) - 1;
    Integer sizeValue = size.orElse(3);
    String sortColValue = sortCol.filter(s -> !s.isBlank()).orElse("id");
    // Модель
    model.addAttribute("products", productService.findProductsByFilter(
            minCost.isPresent() ? minCost.get() : null,
            maxCost.isPresent() ? maxCost.get() : null,
            pageValue,
            sizeValue,
            sortColValue));
    // Уровень View. Указываем какой шаблон используем: /resources/templates/product.html
    // /resources/templates/ - из настроек, которые указаны в maven spring-boot-dependecies
    return "product";
  }

  /**
   * Уровень контроллера. Обрабатываем метод GET URL  .../product/id
   *
   * @PathVariable("id") long id -  входящий параметр URL .../product/{id}
   * Аналогично можно обработать параметры get, которые были переданы через ?user=... - если его нет, то = NULL
   */
  @GetMapping("/{id}")
  public String form(@PathVariable("id") long id, Model model) {
    // Модель
    model.addAttribute("product", productService.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found!")));        // Если такого продукта нет, то усключение
    // Уровень View. Указываем какой шаблон используем: /resources/templates/product_form.html
    return "product_form";
  }

  /**
   * Создание продукта
   * Уровень контроллера. Обрабатываем метод GET URL  .../product/new
   */
  @GetMapping("/new")
  public String form(Model model) {
    model.addAttribute("product", new ProductDto());
    return "product_form";
  }

  /**
   * Удаление продукта
   * Уровень контроллера. Обрабатываем метод DELETE URL  .../product/{id}
   */
  // Если пользователь авторизован, то доступен данный метод
  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}")
  public String remove(@PathVariable("id") long id, Model model) {
    productService.deleteById(id);
    // перенаправление на URL .../product. Ответ кода: 302. Перенаправление: HTML.Head.Location:/product
    return "redirect:/product";
  }


  /**
   * Уровень контроллера. Обрабатываем метод POST URL  .../user/new
   * Без ВАЛИДАЦИИ
   */
//  @PostMapping
//  public String save(Product product) {
//    productRepository.save(product);
//    // перенаправление на URL .../product. Ответ кода: 302. Перенаправление: HTML.Head.Location:/product
//    return "redirect:/product";
//  }

  /**
   * Уровень контроллера. Обрабатываем метод POST URL  .../user/new    * ВАЛИДАЦИя
   *
   * @Valid - объект валидации. BindingResult binding - проверка. Валидация на заполнение полей.
   * @ModelAttribute("product") - так как мы указываем класс ProductDto, а нам нужно преобразовать его в product
   */
  // Если пользователь авторизован, то доступен данный метод
  @PreAuthorize("isAuthenticated()")
  @PostMapping
  public String save(@Valid @ModelAttribute("product") ProductDto product, BindingResult binding) {
    if (binding.hasErrors()) {     // если есть ошибки, то остаемся на форме product_form
      return "product_form";
    }
    productService.save(product);
    // перенаправление на URL .../product. Ответ кода: 302. Перенаправление: HTML.Head.Location:/product
    return "redirect:/product";
  }

  /**
   * Если в процесе работы контроллера возникнет исключение NotFoundException, то будет перехват
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)   // Ошибка 404
  @ExceptionHandler
  public String notFoundExceptionHandler(Model model, NotFoundException e) {
    model.addAttribute("message", e.getMessage());
    return "not_found";
  }
}

package ru.geekbrain.lesson04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrain.lesson04.product.Product;
import ru.geekbrain.lesson04.product.ProductRepository;

import javax.naming.Binding;
import javax.validation.Valid;

/**
 * Создание контроллера обработки URL .../user через DispatcherServlet
 *
 * @Controller - должны быть эта аннатация
 */
@RequestMapping("/product")
@Controller
public class ProductController {

  private final ProductRepository productRepository;

  /**
   * @Autowired - зависимость. создание Bean[объект] ProductRepository, который управляется spring
   */
  @Autowired
  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  /**
   * Уровень контроллера. Обрабатываем метод GET URL  .../product/*
   */
  @GetMapping
  public String listPage(Model model) {
    // Модель
    model.addAttribute("product", productRepository.findAll());
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
    model.addAttribute("product", productRepository.findById(id));
    // Уровень View. Указываем какой шаблон используем: /resources/templates/product_form.html
    return "product_form";
  }

  /**
   * Уровень контроллера. Обрабатываем метод GET URL  .../product/new
   */
  @GetMapping("/new")
  public String form(Model model) {
    model.addAttribute("product", new Product(null, "", 0.0f));
    return "product_form";
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
   * @Valid - объект валидации. BindingResult binding - проверка. Валидация на заполнение полей.
   */
  @PostMapping
  public String save(@Valid Product product, BindingResult binding) {
    if (binding.hasErrors()){     // если есть ошибки, то остаемся на форме product_form
      return "product_form";
    }
    productRepository.save(product);
    // перенаправление на URL .../product. Ответ кода: 302. Перенаправление: HTML.Head.Location:/product
    return "redirect:/product";
  }
}

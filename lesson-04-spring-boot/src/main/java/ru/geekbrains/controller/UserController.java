package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.exception.NotFoundException;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;
import ru.geekbrains.persist.UserSpecification;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/user")
@Controller
public class UserController {

  private final UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public String listPage(@RequestParam Optional<String> usernameFilter,
                         @RequestParam Optional<String> emailFilter,
                         Model model) {
//    Вариант фильтра 1
//    String usernameFilterValue = usernameFilter.filter(s -> !s.isBlank()).orElse(null);
//    String emailFilterValue = emailFilter.filter(s -> !s.isBlank()).orElse(null);
//    model.addAttribute("users", userRepository.findUserByFilter(usernameFilterValue, emailFilterValue));

//    Вариант фильтра 2 (составной запрос в БД [лучше + гибкость подхода]) через класс UserSpecification
    Specification<User> spec = Specification.where(null);
    if (emailFilter.isPresent() && !emailFilter.get().isBlank()){
      spec = spec.and(UserSpecification.emailContaining(emailFilter.get()));
    }
    if (usernameFilter.isPresent() && !usernameFilter.get().isBlank()){
      spec = spec.and(UserSpecification.usernameContaining(usernameFilter.get()));
    }
    model.addAttribute("users", userRepository.findAll(spec));
    return "user";
  }

  @GetMapping("/{id}")
  public String form(@PathVariable("id") long id, Model model) {
    model.addAttribute("user", userRepository.
            findById(id).orElseThrow(() -> new NotFoundException("Product not found!")));        // Если такого продукта нет, то усключение;
    // Уровень View. Указываем какой шаблон используем: /resources/templates/product_form.html
    return "user_form";
  }

  @GetMapping("/new")
  public String form(Model model) {
    model.addAttribute("user", new User(""));
    return "user_form";
  }

  @PostMapping
  public String save(@Valid User user, BindingResult binding) {
    if (binding.hasErrors()) {
      return "user_form";
    }
    if (!user.getPassword().equals(user.getMatchingPassword())) {   // Если поле пароль и второй пароль не одинакова, то создаем ошибку
      // Выдаем сообщение "Password not match"
      binding.rejectValue("password", "", "Password not match");
      return "user_form";
    }
    userRepository.save(user);
    return "redirect:/user";
  }

  /**
   * Удаление продукта
   * Уровень контроллера. Обрабатываем метод DELETE URL  .../product/{id}
   */
  @DeleteMapping("/{id}")
  public String remove(@PathVariable("id") long id, Model model) {
    userRepository.deleteById(id);
    // перенаправление на URL .../product. Ответ кода: 302. Перенаправление: HTML.Head.Location:/product
    return "redirect:/user";
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

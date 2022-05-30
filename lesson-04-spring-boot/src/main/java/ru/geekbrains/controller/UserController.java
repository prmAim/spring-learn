package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.exception.NotFoundException;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listPage(@RequestParam Optional<String> usernameFilter,
                           @RequestParam Optional<String> emailFilter,
                           // пагинация page = № страницы, size = размер страницы
                           @RequestParam Optional<Integer> page,
                           @RequestParam Optional<Integer> size,
                           @RequestParam Optional<String> sortField,
                           Model model) {
        String usernameFilterValue = usernameFilter
                .filter(s -> !s.isBlank())
                .orElse(null);
        String emailFilterValue = emailFilter
                .filter(s -> !s.isBlank())
                .orElse(null);
        // Расчет идет с 0, а пользователь видит с № 1
        Integer pageValue = page.orElse(1) - 1;
        Integer sizeValue = size.orElse(3);
        // Сортировка
        String sortFieldValue = sortField
                .filter(s -> !s.isBlank())
                .orElse("id");
        model.addAttribute("users", userService.findUsersByFilter(
                usernameFilterValue,
                emailFilterValue,
                pageValue,
                sizeValue,
                sortFieldValue));
        return "user";
    }

    @GetMapping("/{id}")
    public String form(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
        // Уровень View. Указываем какой шаблон используем: /resources/templates/product_form.html
        return "user_form";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("user", new UserDto());
        return "user_form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("user") UserDto user, BindingResult binding) {
        if (binding.hasErrors()) {
            return "user_form";
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {   // Если поле пароль и второй пароль не одинакова, то создаем ошибку
            // Выдаем сообщение "Password not match"
            binding.rejectValue("password", "", "Password not match");
            return "user_form";
        }
        userService.save(user);
        return "redirect:/user";
    }

    /**
     * Удаление продукта
     * Уровень контроллера. Обрабатываем метод DELETE URL  .../product/{id}
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        userService.deleteById(id);
        // перенаправление на URL .../product. Ответ кода: 302. Перенаправление: HTML.Head.Location:/product
        return "redirect:/user";
    }

    /**
     * Если в процесе работы контроллера возникнет исключение NotFoundException, то будет перехват
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)      // Ошибка 404
    @ExceptionHandler
    public String notFoundExceptionHandler(Model model, NotFoundException ex) {
        model.addAttribute("message", ex.getMessage());
        return "not_found";
    }
}

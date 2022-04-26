package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

/**
 * Создание контроллера обработки URL .../user через DispatcherServlet
 */
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Уровень контроллера. Обрабатываем метод GET URL  .../user/*
     */
    @GetMapping
    public String listPage(Model model) {
        // Модель
        model.addAttribute("users", userRepository.findAll());
        // Уровень View. Указываем какой шаблон используем: /WEB-INF/views/user.html
        // /WEB-INF/views/ - из настроек AppConfig. html - из настроек AppConfig
        return "user";
    }

    /**
     * Уровень контроллера. Обрабатываем метод GET URL  .../user/id
     * @PathVariable("id") long id -  входящий параметр URL .../user/{id}
     * Аналогично можно обработать параметры get, которые были переданы через ?user=... - если его нет, то = NULL
     */
    @GetMapping("/{id}")
    public String form(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userRepository.findById(id));
        // Уровень View. Указываем какой шаблон используем: /WEB-INF/views/user_form.html
        return "user_form";
    }

    /**
     * Уровень контроллера. Обрабатываем метод GET URL  .../user/new
     */
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("user", userRepository.save(new User("New User")));
        return "user_form";
    }

    /**
     * Уровень контроллера. Обрабатываем метод POST URL  .../user/new
     */
    @PostMapping
    public String save(User user) {
        userRepository.save(user);
        // перенаправление на URL .../user. Ответ кода: 302. Перенаправление: HTML.Head.Location:/user
        return "redirect:/user";
    }
}

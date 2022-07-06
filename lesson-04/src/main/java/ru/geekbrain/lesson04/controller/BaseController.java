package ru.geekbrain.lesson04.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для обработки URL <.../>
 */
@RequestMapping("/")
@Controller
public class BaseController {

  @GetMapping
  public String mainPage() {
    return "main";
  }

  @GetMapping("/login")
  public String authLogin() {
    return "login";
  }

  @GetMapping("/access_denied")
  public String accessDenied() {
    return "access_denied";
  }
}

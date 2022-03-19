package ru.geekbrains;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Класс, который реализует фильтр оправки запросов
 * шаблон проектирования Chain of Responsibility Design Pattern
 */
@WebFilter(urlPatterns = "/*")    // указываем для каких url будет использован фильтр
public class EncodingFilter implements Filter {
  private FilterConfig filterConfig;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
  }

  /**
   * @param request  - запрос от клиента
   * @param response - ответ клиенту
   * @param chain    - цепочка фильтров
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    response.setContentType("text/html");     // обработка фильтром: дополнили кодировку
    response.setCharacterEncoding("utf-8");   // обработка фильтром: дополнили кодировку
    chain.doFilter(request, response);        // передаем запрос далее. Если убрать эту строчку, то будут глушиться все запросы
  }

  @Override
  public void destroy() {
  }
}

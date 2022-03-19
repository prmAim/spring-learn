package ru.geekbrains;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * Класс, реализует интерфейс Servlet
 * Сайт: myFirstServlet
 */
@WebServlet(urlPatterns = "/myFirstServlet")    // шаблон, который указывает какие URL будет обрабатывать сервелет
public class TheFirstServlet implements Servlet {

  // связь между сервером приложений и сервелетом
  private ServletConfig сonfig;

  /**
   * Начало. Инициализация класса
   */
  @Override
  public void init(ServletConfig servletConfig) throws ServletException {
    this.сonfig = servletConfig;
  }

  @Override
  public ServletConfig getServletConfig() {
    return сonfig;
  }

  /**
   * Метод обработки запросов
   *
   * @param servletRequest  - входящий запрос
   * @param servletResponse - ответ на входящий запрос
   */
  @Override
  public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
    // ВАЖНО!!! Если будет несколько запросов, тонужно использовать потокобезопасныек классы!!!

    servletResponse.getWriter().println("<h2>Hello world!!!</h2>");
  }

  @Override
  public String getServletInfo() {
    return null;
  }

  /**
   * Метод освобождения ресурсов
   */
  @Override
  public void destroy() {

  }
}

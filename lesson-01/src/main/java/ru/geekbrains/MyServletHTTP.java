package ru.geekbrains;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс, расширяет класс HttpServlet
 * Сайт: myFirstServlet
 */
@WebServlet(urlPatterns = "/my_servlet_HTTP/*")
public class MyServletHTTP extends HttpServlet {

  /**
   * Переопределение метода Get
   *
   * @param req  - запрос от клиента
   * @param resp - ответ клиенту
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // ВАЖНО!!! Если будет несколько запросов, тонужно использовать потокобезопасныек классы!!!

    resp.getWriter().println("<h2>Мой сайт передает Привет!</h2>");
    // Пример: http://localhost:8080/servlet-app/my_servlet_HTTP/page1?id=1
    // URL: req.getScheme() + "://" + req.getServerName() + ":" + String.valueOf(req.getServerPort()) + req.getContextPath() + req.getServletPath() + req.getPathInfo() + req.getQueryString()
    resp.getWriter().println("<p>getScheme: " + req.getScheme() + "</p>");                // Протокол, где: http
    resp.getWriter().println("<p>getServerName: " + req.getServerName() + "</p>");        // Сервер, где: localhost
    resp.getWriter().println("<p>getServerPort: " + String.valueOf(req.getServerPort()) + "</p>");  // Порт, где:  8080
    resp.getWriter().println("<p>contextPath: " + req.getContextPath() + "</p>");         // часть URL, где домен: /servlet-app
    resp.getWriter().println("<p>servletPath: " + req.getServletPath() + "</p>");         // часть URL, где обрабатывается Сервлет: /my_servlet_HTTP (смотреть на анатацию)
    resp.getWriter().println("<p>pathInfo: " + req.getPathInfo() + "</p>");               // часть URL, где домен: /page1
    resp.getWriter().println("<p>queryString: " + req.getQueryString() + "</p>");         // часть URL, где домен: param1=1
    resp.getWriter().println("<p>param1: " + req.getParameter("id") + "</p>");      // часть URL, где домен: 1
    resp.getWriter().println("<p>param2: " + req.getParameter("param2") + "</p>");  // часть URL, где домен: null
  }
}

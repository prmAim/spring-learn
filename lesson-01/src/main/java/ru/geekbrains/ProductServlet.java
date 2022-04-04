package ru.geekbrains;

import ru.geekbrains.product.Product;
import ru.geekbrains.product.ProductMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/product_servlet/*")
public class ProductServlet extends HttpServlet {
  private static final Pattern PARAM_PATTERN = Pattern.compile("\\/(\\d+)");    //  регулярные выражения
  private ProductMap productMap;

  @Override
  public void init() throws ServletException {
    // получаем коллекцию <Продуктов> из общего контекста
    this.productMap = (ProductMap) getServletContext().getAttribute("productMap");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // ВАЖНО!!! Если будет несколько запросов, нужно использовать потокобезопасныек классы!!!
    PrintWriter context = resp.getWriter();

    if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
      context.println("<table border =1 >");
      context.println("<tr>");
      context.println("<th>ID</th>");
      context.println("<th>Товар</th>");
      context.println("<th>Стоимость</th>");
      context.println("</tr>");

      for (Product product : productMap.fiandAll()) {
        context.println("<tr>");
        context.println("<td><a href='" + getServletContext().getContextPath() + "/product_servlet/" + product.getId() + "'>" + product.getId() + "</a></td>");
        context.println("<td>" + product.getTitle() + "</td>");
        context.println("<td>" + product.getCost() + "</td>");
        context.println("</tr>");
      }

      context.println("</table>");
    } else {
      Matcher matcher = PARAM_PATTERN.matcher(req.getPathInfo());  // проверка на соотвествие регулярного выражения
      if (matcher.matches()) {
        long id = Long.parseLong(matcher.group(1));                // ИД пользователя из адресной строки
        Product product = this.productMap.findById(id);            // получаем класс продукта

        if (product == null) {                                     // Если такого пользователя нет, то выдаем сообщение об ошибке
          resp.getWriter().println("<p>Product not found</p>");
          resp.setStatus(404);
          return;
        }
        context.println("<table border =1 >");
        context.println("<tr>");
        context.println("<th>ID</th>");
        context.println("<th>Товар</th>");
        context.println("<th>Стоимость</th>");
        context.println("</tr>");
        context.println("<tr>");
        context.println("<td>" + product.getId() + "</td>");
        context.println("<td>" + product.getTitle() + "</td>");
        context.println("<td>" + product.getCost() + "</td>");
        context.println("</tr>");
        context.println("</table>");
      } else {
        resp.getWriter().println("<p>Bad parameters</p>");
        resp.setStatus(400);
      }
    }

  }
}

package ru.geekbrains;

import ru.geekbrains.prdct.Product;
import ru.geekbrains.prdct.ProductMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/product_servlet/*")
public class ProductServlet extends HttpServlet {

  private ProductMap productMap;

  @Override
  public void init() throws ServletException {
    this.productMap = new ProductMap();
    productMap.insert(new Product("Виноград", 140.05f));
    productMap.insert(new Product("Бананы", 110.10f));
    productMap.insert(new Product("Киви", 60.0f));
    productMap.insert(new Product("Гранат", 240.0f));
    productMap.insert(new Product("Яблоки", 95.50f));
    productMap.insert(new Product("Авокадо", 50.95f));
    productMap.insert(new Product("Персики", 250.10f));
    productMap.insert(new Product("Арбуз", 100.0f));
    productMap.insert(new Product("Апельсин", 120.30f));
    productMap.insert(new Product("Мандарин", 145.40f));
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // ВАЖНО!!! Если будет несколько запросов, тонужно использовать потокобезопасныек классы!!!

    PrintWriter context = resp.getWriter();
    context.println("<table border =1 >");
    context.println("<tr>");
    context.println("<th>ID</th>");
    context.println("<th>Товар</th>");
    context.println("<th>Стоимость</th>");
    context.println("</tr>");

    // Полный URL для ссылки
    String hrefBegin = req.getScheme() + "://" + req.getServerName() + ":" + String.valueOf(req.getServerPort()) + req.getContextPath() + req.getServletPath() + "?id=";

    for (Product product : productMap.fiandAll()) {
      if (req.getPathInfo() == null && req.getParameter("id") == null) {
        context.println("<tr>");
        context.println("<td><a href='" + hrefBegin + product.getId() + "'>" + product.getId() + "</a></td>");
        context.println("<td>" + product.getTitle() + "</td>");
        context.println("<td>" + product.getCost() + "</td>");
        context.println("</tr>");
      } else {
        if (product.getId() == Integer.valueOf(req.getParameter("id"))) {
          context.println("<tr>");
          context.println("<td>" + product.getId() + "</td>");
          context.println("<td>" + product.getTitle() + "</td>");
          context.println("<td>" + product.getCost() + "</td>");
          context.println("</tr>");
        }
      }
    }
    context.println("</table>");
  }
}

package ru.geekbrains.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Создание сущности <Продукт>
 */
@Entity                                                   // обязательное поле
@Table(name = "products")
// создание шаблонов-запросов в DB
@NamedQueries({@NamedQuery(name = "findAllProducts", query = "select p from Product p"),
               @NamedQuery(name = "countAllProducts", query = "select count(p) from Product p"),
               @NamedQuery(name = "deleteProduct", query = "delete from Product p where p.id = :id")
})
public class Product {

  @Id                                                     // обязательное поле
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)     // создание id в DB
  private Long id;

  @Column(name = "name", nullable = false, unique = true) // Уникальное поле, не пустое
  private String name;

  @Column(name = "cost")
  private BigDecimal cost;

  @OneToMany(mappedBy = "product")
  List<LineItem> lineItems;

  // Если нет необходимости в отображении какого-либо поля, необходимо пометить его аннотацией @Transient.

  public Product() {                                      // Обязательно пустой конструктор
  }

  public Product(String name, BigDecimal  cost) {
    this.name = name;
    this.cost = cost;
  }

  public Product(Long id, String name, BigDecimal cost) {
    this.id = id;
    this.name = name;
    this.cost = cost;
  }

  // Обязательно GET и SET всех полей в DB
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public List<LineItem> getLineItems() {
    return lineItems;
  }

  public void setLineItems(List<LineItem> lineItems) {
    this.lineItems = lineItems;
  }

  @Override
  public String toString() {
    return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", cost=" + cost +
            '}';
  }
}
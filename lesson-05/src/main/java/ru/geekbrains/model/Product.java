package ru.geekbrains.model;

import javax.persistence.*;

/**
 * Создание сущности <Продукт>
 */
@Entity                                                   // обязательное поле
@Table(name = "products")
public class Product {

  @Id                                                     // обязательное поле
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)     // создание id в DB
  private Long id;

  @Column(name = "name", nullable = false, unique = true) // Уникальное поле, не пустое
  private String name;

  @Column(name = "cost")
  private float cost;

  // Если нет необходимости в отображении какого-либо поля, необходимо пометить его аннотацией @Transient.

  public Product() {                                      // Обязательно пустой конструктор
  }

  public Product(String name, float cost) {
    this.name = name;
    this.cost = cost;
  }

  public Product(Long id, String name, float cost) {
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

  public float getCost() {
    return cost;
  }

  public void setCost(float cost) {
    this.cost = cost;
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
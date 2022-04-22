package ru.geekbrains.product;

import java.util.Objects;

/**
 * Класс продукты
 */
public class Product {
  private long id;
  private String title;
  private float cost;

  public Product(String title, float cost) {
    this.title = title;
    this.cost = cost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return id == product.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public float getCost() {
    return cost;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setCost(float cost) {
    this.cost = cost;
  }
}

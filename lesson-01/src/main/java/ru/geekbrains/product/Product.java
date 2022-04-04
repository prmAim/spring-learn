package ru.geekbrains.product;

public class Product {
  private long id;
  private String title;
  private float cost;

  public Product(String title, float cost) {
    this.title = title;
    this.cost = cost;
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

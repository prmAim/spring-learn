package ru.geekbrain.lesson04.product;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class Product {

  private Long id;

  // @NotBlank - валидация на сервере. Значение не пустая, без пробелов, без табуляции
  // @NotEmpty - валидация на сервере. Значение не пустая, но пропустить пробелы, табуляция ...
  // @NotNull  - валидация на сервере. Значение не Null.
  // @Email    - валидация на сервере. Значение на эл. почту.
  // @Pattern  - Валидация. регулярные выражения: одни буквы не менее 2 шт. Без точек
  // message   - сообщение при ошибке валидации
  @Pattern(regexp = "^(?=.*?[A-Z]).{2,}$", message = "Name product is unknown")
  @NotBlank
  private String name;

  @Max(value = 100000, message = "Max cost is 100 000")
  @Min(value = 0, message = "Min cost is 0")
  private float cost;

  public Product(Long id, String name, float cost) {
    this.id = id;
    this.name = name;
    this.cost = cost;
  }

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

  public float getCost() {return cost;}

  public void setCost(float cost) {this.cost = cost;}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
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

package ru.geekbrain.lesson04.product;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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

  // @NotBlank - валидация на сервере. Значение не пустая, без пробелов, без табуляции
  // @NotEmpty - валидация на сервере. Значение не пустая, но пропустить пробелы, табуляция ...
  // @NotNull  - валидация на сервере. Значение не Null.
  // @Email    - валидация на сервере. Значение на эл. почту.
  // @Pattern  - Валидация. регулярные выражения: одни буквы не менее 2 шт. Без точек
  // message   - сообщение при ошибке валидации
  @Pattern(regexp = "^(?=.*?[A-Z]).{2,}$", message = "Name product is unknown")
  @NotBlank
  @Column(name = "name", nullable = false, unique = true) // Уникальное поле, не пустое
  private String name;

  @Max(value = 100000, message = "Max cost is 100 000")
  @Min(value = 0, message = "Min cost is 0")
  @Column(name = "cost")
  private float cost;
//
//  @ManyToMany
//  @JoinTable(
//          name = "users_products",
//          joinColumns = @JoinColumn(name = "products_id"),
//          inverseJoinColumns = @JoinColumn(name = "users_id")
//  )
//  List<User> users = new ArrayList<>();

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
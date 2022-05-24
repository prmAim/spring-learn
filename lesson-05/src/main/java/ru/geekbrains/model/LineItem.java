package ru.geekbrains.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Связь МногоеКоМногим <User><Product> через эту сущность <LineItem>
 */
@Entity
@Table(name = "line_items",
       indexes = @Index(name = "ux_user_product", columnList = "user_id, product_id", unique = true) // Создание составного индеса
)
public class LineItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "qty", nullable = false)
  private Long qty;

  @Column(name = "size")
  private String size;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public LineItem() {
  }

  public LineItem(BigDecimal price, Long qty, String size, Product product, User user) {
    this.price = price;
    this.qty = qty;
    this.size = size;
    this.product = product;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Long getQty() {
    return qty;
  }

  public void setQty(Long qty) {
    this.qty = qty;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}

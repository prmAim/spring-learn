package ru.geekbrains.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
// Шаблон - запросы
@NamedQueries({
        @NamedQuery(name = "deleteUsers", query = "delete from User u where u.id = :id"),
        @NamedQuery(name = "allUsers", query = "select u from User u"),
        @NamedQuery(name = "countAllUsers", query = "select count(u) from User u")
})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column
  private String email;

  @Column(nullable = false, length = 1024)
  private String password;

  // Связь сущност. <User> и <Contact>
  // Виды связей @ManyToOne, @OneToOne, @OneToMany
  // Важно не забыть про GET и SET
  @ManyToMany
  @JoinTable(
          name = "users_products",
          joinColumns = @JoinColumn(name = "users_id"),
          inverseJoinColumns = @JoinColumn(name = "products_id")
  )
  private List<Product> products = new ArrayList<>();

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}

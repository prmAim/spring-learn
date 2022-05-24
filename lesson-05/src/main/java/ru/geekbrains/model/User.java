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
  @OneToMany(mappedBy = "product")
  private List<LineItem> lineItems;

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

  public List<LineItem> getLineItems() {
    return lineItems;
  }

  public void setLineItems(List<LineItem> lineItems) {
    this.lineItems = lineItems;
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

package ru.geekbrain.lesson04.persist;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String namerole;

  @ManyToMany (mappedBy = "roles")
  private Set<User> users;

  public Role() {
  }

  public Role(Long id, String namerole, Set<User> users) {
    this.id = id;
    this.namerole = namerole;
    this.users = users;
  }

  public Role(String namerole) {
    this.namerole = namerole;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNamerole() {
    return namerole;
  }

  public void setNamerole(String namerole) {
    this.namerole = namerole;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Role role = (Role) o;
    return id.equals(role.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

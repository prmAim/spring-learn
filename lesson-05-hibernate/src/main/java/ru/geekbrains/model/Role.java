package ru.geekbrains.model;

import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "nameRole")
  private String nameRole;

  @ManyToMany
  private List<User> users = new ArrayList<>();

  public Role() {
  }


}

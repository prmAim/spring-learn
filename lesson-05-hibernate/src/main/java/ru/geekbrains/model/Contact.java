package ru.geekbrains.model;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  // создание поле, в котором перечесляемый тип(хранит в DB как Число <EnumType.STRING> или строковый тип <EnumType.STRING>)
  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private ContactType type;

  @Column(name = "description", nullable = false)
  private String description;

  // Связь сущност. <User> и <Contact>
  // Виды связей @ManyToOne, @OneToOne, @OneToMany
  // Важно не забыть про GET и SET
  @ManyToOne
  private User user;

  public Contact() {
  }

  public Contact(ContactType type, String description, User user) {
    this.type = type;
    this.description = description;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ContactType getType() {
    return type;
  }

  public void setType(ContactType type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public enum ContactType{
    MOBILE_PHOME, HOME_PHOME, WORK_PHOME, WORK_EMAIL, HOME_ADDRESS
  }

  @Override
  public String toString() {
    return "Contact{" +
            "id=" + id +
            ", type=" + type +
            ", description='" + description + '\'' +
            ", user=" + user +
            '}';
  }
}

package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;

// Для создания сложно запроса к БД через criteriaBuilder
public final class UserSpecification {

  public static Specification<User> usernameContaining(String username) {
    return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + username + "%"));
  }

  public static Specification<User> emailContaining(String email) {
    return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + email + "%"));
  }
}

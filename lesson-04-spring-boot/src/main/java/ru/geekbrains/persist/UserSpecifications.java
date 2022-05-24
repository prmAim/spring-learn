package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.User;

// Для создния фильтра
public final class UserSpecifications {

    public static Specification<User> usernameContaining(String username) {
        return (root, query, cb) -> cb.like(root.get("username"), "%" + username + "%");
    }

    public static Specification<User> emailContaining(String email) {
        return (root, query, cb) -> cb.like(root.get("email"), "%" + email + "%");
    }
}

package ru.geekbrains.model;

import ru.geekbrains.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    /**
     * Найти всех <Пользователя>
     */
    List<User> findAll();

    /**
     * Найти <Пользователя> по ID
     */
    Optional<User> findById(long id);

    /**
     * Сохранение <Пользователя>
     */
    void save(User product);

    /**
     * Удаление <Пользователя>
     */
    void delete(long id);
}

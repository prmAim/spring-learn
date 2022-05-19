package ru.geekbrains.model;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    /**
     * Найти всех <Пользователя>
     * @return
     */
    List<User> findAll();

    /**
     * Найти <Пользователя> по ID
     * @return
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

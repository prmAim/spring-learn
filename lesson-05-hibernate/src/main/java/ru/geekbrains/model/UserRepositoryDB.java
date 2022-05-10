package ru.geekbrains.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class UserRepositoryDB implements UserRepository {

  // потокобезопасный класс
  private final EntityManagerFactory emFactory;

  public UserRepositoryDB(EntityManagerFactory emFactory) {
    this.emFactory = emFactory;
  }

  @Override
  public List<User> findAll() {
    // Использован подготовленный запрос <allUsers> в entity <User> с помощью createNamedQuery
    return executeForEntityManager(em -> em.createNamedQuery("allUsers", User.class).getResultList());
  }

  @Override
  public Optional<User> findById(long id) {
    return executeForEntityManager(em -> Optional.ofNullable(em.find(User.class, id)));
  }

  /**
   * Единичный запрос
   */
  public long count() {
    return executeForEntityManager(em -> em.createQuery("select count(u) from User u", Long.class).getSingleResult());
  }

  @Override
  public void save(User user) {
    executeSaveEntityManager(em -> {
      if (user.getId() != null) {
        em.persist(user);
      } else {
        em.merge(user);
      }
    });
  }

  @Override
  public void delete(long id) {
    executeSaveEntityManager(em -> {
      // Использован подготовленный запрос <deleteUsers> в entity <User> с помощью createNamedQuery
      em.createNamedQuery("deleteUsers").setParameter("id", id).executeUpdate();
    });
  }

  /**
   * Общий блок для поиска <Пользователь> по ID, list<Пользователь>
   */
  private <R> R executeForEntityManager(Function<EntityManager, R> func) {
    EntityManager em = emFactory.createEntityManager();
    try {
      return func.apply(em);      // передаем нашу функцию
    } finally {
      em.close();
    }
  }

  /**
   * Процедура для сохранения, удаления <Пользователь>
   */
  private void executeSaveEntityManager(Consumer<EntityManager> consumer) {
    EntityManager em = emFactory.createEntityManager();
    try {
      em.getTransaction().begin();
      consumer.accept(em);      // передаем нашу функцию
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }

}

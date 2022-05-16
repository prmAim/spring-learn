package ru.geekbrains.model;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class UserRepositoryDB extends SessionDBLayer implements UserRepository {

  public UserRepositoryDB(EntityManagerFactory emFactory) {
    super(emFactory);
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
}

package ru.geekbrains.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;
import java.util.function.Function;

public class SessionDBLayer {

  // потокобезопасный класс
  private final EntityManagerFactory emFactory;

  public SessionDBLayer(EntityManagerFactory emFactory) {
    this.emFactory = emFactory;
  }

  /**
   * Общий блок для поиска R по ID, list<R>
   */
  protected <R> R executeForEntityManager(Function<EntityManager, R> func) {
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
  protected void executeSaveEntityManager(Consumer<EntityManager> consumer) {
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

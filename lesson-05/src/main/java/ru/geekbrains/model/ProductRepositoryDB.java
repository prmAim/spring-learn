package ru.geekbrains.model;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса. DAO ProductRepository
 */
public class ProductRepositoryDB extends SessionDBLayer implements ProductRepository {

  public ProductRepositoryDB(EntityManagerFactory emFactory) {
    super(emFactory);
  }

// Вариант 1 Найти всех
//  @Override
//  public List<Product> findAll() {
//    EntityManager em = emFactory.createEntityManager();
//    try {
//      return em.createQuery("select p from Product p", Product.class).getResultList();
//    } catch (Exception e) {
//      e.printStackTrace();
//    } finally {
//      em.close();
//      return null;
//    }
//  }

//  Вариант 1 Найти продукт по ID
//  @Override
//  public Optional<Product> findById(long id) {
//    EntityManager em = emFactory.createEntityManager();
//    try {
//      return Optional.ofNullable(em.find(Product.class, id));
//    } catch (Exception e) {
//      e.printStackTrace();
//    } finally {
//      em.close();
//      return null;
//    }
//  }

//  Вариант 1. Сохранить и создать продукт
//  @Override
//  public void save(Product product) {
//    EntityManager em = emFactory.createEntityManager();
//    try {
//      em.getTransaction().begin();
//      em.merge(product);
//      em.getTransaction().commit();
//    } catch (Exception e) {
//      e.printStackTrace();
//    } finally {
//      em.close();
//    }
//    System.out.println("LOG: Product inserted");
//  }

//  Вариант 1 Единичный запрос
//  public long count() {
//    EntityManager em = emFactory.createEntityManager();
//    try {
//      return em.createQuery("select count(p) from Product p", Long.class).getSingleResult();
//    } catch (Exception e) {
//      e.printStackTrace();
//    } finally {
//      em.close();
//      return 0;
//    }
//  }

//  Вариант 1. Сохранить и создать продукт
//  @Override
//  public void save(Product product) {
//    EntityManager em = emFactory.createEntityManager();
//    try {
//      em.getTransaction().begin();
//      if (product.getId() != null) {
//        em.persist(product);
//      } else {
//        em.merge(product);
//      }
//      em.getTransaction().commit();
//    } catch (Exception e) {
//      em.getTransaction().rollback();
//    } finally {
//      em.close();
//    }
//    System.out.println("LOG: Product inserted");
//  }

//  Вариант 1. Удаления продукта
//  @Override
//  public void delete(long id) {
//    EntityManager em = emFactory.createEntityManager();
//    try {
//      em.getTransaction().begin();
//      Product product = em.find(Product.class, id);
//      em.remove(product);
//      em.getTransaction().commit();
//    } catch (Exception e) {
//      em.getTransaction().rollback();
//    } finally {
//      em.close();
//    }
//    System.out.println("LOG: Product deleted");
//  }

  // Вариант 2 Найти всех
  @Override
  public List<Product> findAll() {
    // Использован подготовленный запрос <findAllProducts> в entity <Product> с помощью createNamedQuery
    return executeForEntityManager(em -> em.createNamedQuery("findAllProducts", Product.class).getResultList());
  }

  // Вариант 2 Найти продукт по ID
  @Override
  public Optional<Product> findById(long id) {
    return executeForEntityManager(em -> Optional.ofNullable(em.find(Product.class, id)));
  }

  /**
   * Вариант 2 Единичный запрос
   */
  public long count() {
    return executeForEntityManager(em -> em.createQuery("select count(p) from Product p", Long.class).getSingleResult());
  }

  //  Вариант 2. Сохранить и создать продукт
  @Override
  public void save(Product product) {
    executeSaveEntityManager(em -> {
      if (product.getId() != null) {
        em.persist(product);
      } else {
        em.merge(product);
      }
    });
  }

  //  Вариант 2. Удаления продукта
  @Override
  public void delete(long id) {
    executeSaveEntityManager(em -> {
      // Использован подготовленный запрос <deleteProduct> в entity <Product> с помощью createNamedQuery
      em.createNamedQuery("deleteProduct").setParameter("id", id).executeUpdate();
    });
  }
}

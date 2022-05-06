package ru.geekbrains.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Реализация интерфейса. DAO ProductRepository
 */
public class ProductRepositoryDB implements ProductRepository {

  private final EntityManagerFactory emFactory;

  public ProductRepositoryDB(EntityManagerFactory emFactory) {
    this.emFactory = emFactory;
  }

  @Override
  public List<Product> findAll() {
    EntityManager em = emFactory.createEntityManager();
    try {
      List<Product> products = em.createQuery("select p from Product p", Product.class).getResultList();
      products.forEach(System.out::println);
      return products;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      em.close();
      return null;
    }
  }

  @Override
  public Product findById(long id) {
    EntityManager em = emFactory.createEntityManager();
    try {
      Product product = em.find(Product.class, id);
      if (product == null){
        System.out.println("LOG: Product not found");
      } else {
        System.out.println("LOG: Product by id: " + product.toString());
      }
      return product;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      em.close();
      return null;
    }
  }

  @Override
  public void save(Product product) {
    EntityManager em = emFactory.createEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(product);
      em.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      em.close();
    }
    System.out.println("LOG: Product inserted");
  }

  @Override
  public void delete(long id) {
    EntityManager em = emFactory.createEntityManager();
    try {
      em.getTransaction().begin();
      Product product = em.find(Product.class, id);
      em.remove(product);
      em.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      em.close();
    }
    System.out.println("LOG: Product deleted");
  }
}

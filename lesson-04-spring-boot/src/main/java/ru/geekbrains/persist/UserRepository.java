package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//NEW SCHOOL = JpaRepository<User, Long>
// Создание фильтра через JpaSpecificationExecutor<User> с помощью  criteriaBuilder
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    // То, что было на уроке это баг в Hibernate, который пока не исправлен
    // Временное решение - указать аннотацию @Param для всех параметров
    // https://github.com/spring-projects/spring-data-jpa/issues/2472
    @Query("select u " +
            " from User u " +
            "where (u.username like concat('%', :username, '%') or :username is null) and " +
            "      (u.email like concat('%', :email, '%') or :email is null)")
    List<User> findUserByFilter(@Param("username") String username,
                                @Param("email") String email);

    Optional<User> findUserByUsername(@Param("username") String username);

}

// Old SCHOOL
//import org.springframework.stereotype.Repository;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Optional;
//
//// DAO для объекта User
//@Repository
//public class UserRepository {
//
//  // Inject из Spring Context. @PersistenceContext учитывает создание сессий под каждую операцию с БД.
//  @PersistenceContext
//  private EntityManager em;
//
////  // Создание транзакции
////  @Resource
////  private UserTransaction transaction;
////
////  // Без создания транзакции будет ошибка. Так как в объекта [this] транзакция в proxy еще не создана.
////  @PostConstruct
////  public void init() {
////    try {
////      transaction.begin();
////      this.save(new User("User 1"));
////      this.save(new User("User 2"));
////      this.save(new User("User 3"));
////      this.save(new User("User 4"));
////      this.save(new User("User 5"));
////      transaction.commit();
////    } catch (Exception e) {
////      try {
////        transaction.rollback();
////      } catch (SystemException ex) {
////        ex.printStackTrace();
////      }
////    }
////  }
//
//  public List<User> findAll() {
//    return em.createQuery("select u from User u", User.class).getResultList();
//  }
//
//  public Optional<User> findById(long id) {
//    return Optional.ofNullable(em.find(User.class, id));
//  }
//
//  @Transactional
//  public User save(User user) {
//    if (user.getId() == null) {
//      em.persist(user);
//    } else {
//      em.merge(user);
//    }
//    return user;
//  }
//
//  @Transactional
//  public void deleteById(long id) {
//    em.createQuery("delete from User u where u.id = :id").setParameter("id", id).executeUpdate();
//  }
//
//}

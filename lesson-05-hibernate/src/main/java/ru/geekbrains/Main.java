package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

//         INSERT
        em.getTransaction().begin();

        em.persist(new User("User 1", "aaffa@bffdbb@.cc", "apaaapappappa"));
        em.persist(new User("User 2", "aadda@bdddbb@.cc", "apapapfffappa"));
        em.persist(new User("User 3", "aassaaa@bbb@.cc", "ahhhhfdapappappa"));

        em.getTransaction().commit();

        // SELECT
//        User user = em.find(User.class, 1L);
//        System.out.println("User with id 1 " + user);

        // SELECT HQL/JPQL
//        List<User> users = em.createQuery("from User u where u.id in (1, 2)", User.class)
//                .getResultList();
//        users.forEach(System.out::println);

        // UPDATE
        // 1
//        em.getTransaction().begin();
//
//        User user = em.find(User.class, 1L);
//        user.setUsername("User 111");
//
//        em.getTransaction().commit();

        // 2
//        em.getTransaction().begin();
//
//        User user = new User("User 222", "user_222@mail.com", "super_password_123");
//        user.setId(2L);
//        em.merge(user);
//
//        em.getTransaction().commit();

        // DELETE
        em.getTransaction().begin();

        User user = em.find(User.class, 2L);
        em.remove(user);

//        em.createQuery("delete from User u where u.id = 123")
//                .executeUpdate();

        em.getTransaction().commit();

        em.close();

        emFactory.close();
    }
}

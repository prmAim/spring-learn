package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.model.Contact;
import ru.geekbrains.model.User;
import ru.geekbrains.model.UserRepository;
import ru.geekbrains.model.UserRepositoryDB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Подключение к БД через JPA Hibernate, используя настройки подключения hibernate.cfg.xml
        // Получаем фабрику менеджеров сущностей
        EntityManagerFactory emFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

        // Insert Contact по одному User, где id = 2
        User user = em.find(User.class, 2L);
        em.getTransaction().begin();
        em.persist(new Contact(Contact.ContactType.HOME_PHOME, "227788", user));
        em.persist(new Contact(Contact.ContactType.HOME_ADDRESS, "Забайкалье", user));
        em.persist(new Contact(Contact.ContactType.MOBILE_PHOME, "89288791", user));
        em.getTransaction().commit();

        // Insert User и Contact
        em.getTransaction().begin();
        User user4 = new User("User4", "mail@yandex.ru", "123456");
        user4.getContactList().add(new Contact(Contact.ContactType.HOME_ADDRESS, "Moskov", user4));
        user4.getContactList().add(new Contact(Contact.ContactType.HOME_PHOME, "8349", user4));
        user4.getContactList().add(new Contact(Contact.ContactType.WORK_PHOME, "890722", user4));
        em.getTransaction().commit();

        // Select fetch = FetchType.LAZY [ленивая загрузка]
        List<User> users = em.createNamedQuery("allUsers", User.class).getResultList();
        List<Contact> contacts = users.get(2).getContactList();

        // НЕЛЬЗЯ!!! При [ленивая загрузка] так делать нельзя, так как для для каждого Uses будет создавать проблема N+1
        // Лишние запросы.
//        for (User user : users){
//            user.getContactList().forEach(System.out:: println);
//        }

        // РЕШЕНИЕ!!! fetch - [жадная загрузка]. Именно для данного запроса используем fetch
        // Всего один запрос!!!
        List<User> users0 = em.createQuery("select distinct u from User u inner join fetch Contact c on u.id = c.id", User.class).getResultList();
        for (User user0 : users0){
            user0.getContactList().forEach(System.out:: println);
        }

        // Delete. Если установлена orphanRemoval = true, то удалит контакт. Иначе контакт не удалит.
        em.getTransaction().begin();
        User user1 = em.find(User.class, 2L);
        user1.getContactList().remove(0);
        em.merge(user1);
        em.getTransaction().commit();

        em.close();
    }
}

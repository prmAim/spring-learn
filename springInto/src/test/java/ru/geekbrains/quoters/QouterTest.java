package test.java.ru.geekbrains.quoters;

import main.java.ru.geekbrains.qouters.Qouter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QouterTest {
    static ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext("main/resources/applicationContext.xml");
    }

    @Test
    public void sayQoute() throws InterruptedException {
            context.getBean("qouter", Qouter.class).sayQoute();
    }
}
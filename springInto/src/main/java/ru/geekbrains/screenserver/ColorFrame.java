package main.java.ru.geekbrains.screenserver;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Component;

        import javax.swing.*;
        import java.awt.*;
        import java.util.Random;

/**
 * Цель: как обновить prototype в singleton
 * Вариант 1) НЕ верный вариант: когда добавляем ApplicationContext в бизнес логику. Теперь вся логика зависит от Context. При initTest это нужно будет все за собой тянуть.
 * Вариант 2) через абстрактный класс
 */
/*
@Component
public class ColorFrame extends JFrame {        // type = singletone
    @Autowired
    private Color color;                        // type= prototype

    public void showOnRandomPlace() {
        Random random = new Random();
        setLocation(random.nextInt(1200), random.nextInt(700));
        getContentPane().setBackground(color);          // НАДО вызыввать из ColorFrame(singletone) постоянно новый color(prototype). В таком виде  Color будет всегда singletone.
        repaint();
    }
}
*/

/*    Вариант 1) НЕ верный вариант: когда добавляем ApplicationContext в бизнес логику. Теперь вся логика зависит от Context. При initTest это нужно будет все за собой тянуть.
@Component
public class ColorFrame extends JFrame {        // type = singletone
    @Autowired
    private ApplicationContext context;

    public void showOnRandomPlace(){
        Random random = new Random();
        setLocation(random.nextInt(1200), random.nextInt(700));
        getContentPane().setBackground(context.getBean(Color.class));       //Теперь вся логика зависит от Context. При initTest это нужно будет ВЕСЬ SpringContext за собой тянуть.
        repaint();
    }
}
*/
public abstract class ColorFrame extends JFrame {

    // Общая часть у всех
    public ColorFrame() {
        setSize(200, 200);
        setVisible(true);
        // Выключать программу по закрытию фрейма
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showOnRandomPlace() {
        Random random = new Random();
        setLocation(random.nextInt(1200), random.nextInt(700));
        getContentPane().setBackground(getColor());     // НАДО вызыввать из ColorFrame(singletone) постоянно новый Сolor(prototype).
        repaint();
    }

    // связь с
    protected abstract Color getColor();        // а сам вызов bean прописывается в Config
}
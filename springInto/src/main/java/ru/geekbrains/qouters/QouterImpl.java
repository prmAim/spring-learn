package main.java.ru.geekbrains.qouters;

import main.java.ru.geekbrains.annatation.InjectRandomInt;
import main.java.ru.geekbrains.annatation.PostProxy;
import main.java.ru.geekbrains.annatation.Profiling;

import javax.annotation.PostConstruct;

@Profiling
public class QouterImpl implements Qouter {

    @InjectRandomInt(min = 2, max = 7)
    private int repeat;
    private String message;

    /**
     * Фаза 1 = конструктор. ВАЖНО!!! repeat еще НЕ проинициализирована аннотацией  @InjectRandomInt
     */
    public QouterImpl() {
        System.out.println("Phase1 repeat=" + repeat);
    }

    /**
     * Фаза 2 = инициализатор ВАЖНО!!! repeat еще проинициализирована аннотацией  @InjectRandomInt
     */
    @PostConstruct
    public void init(){
        System.out.println("Phase2 repeat=" + repeat);
    }

    @Override
    @PostProxy
    public void sayQoute() {
        System.out.println("Phase3 repeat=" + repeat);
        for (int i = 0; i < repeat; i++) {
            System.out.println(message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

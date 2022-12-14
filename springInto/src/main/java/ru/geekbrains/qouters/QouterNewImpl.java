package main.java.ru.geekbrains.qouters;

import main.java.ru.geekbrains.annatation.InjectRandomInt;

public class QouterNewImpl extends QouterImpl implements Qouter {

    @InjectRandomInt(min = 2, max = 7)   // своя аннатация создание random числа
    private int repeat;

    @Override
    public void sayQoute() {
        System.out.println("Phase3 repeat in " + this.getClass().getName() + " =" + repeat);
    }
}

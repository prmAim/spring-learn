package main.java.ru.geekbrains.annatation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Собственная аннатация для генерации случайных чисел
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomInt {
    int min();
    int max();
}

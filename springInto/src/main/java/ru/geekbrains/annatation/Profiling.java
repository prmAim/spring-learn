package main.java.ru.geekbrains.annatation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Собственная аннатация для замера работы создания bean
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Profiling {
}

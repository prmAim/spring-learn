package main.java.ru.geekbrains.annatation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Собственная аннатация для пометки, что класс устарел
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DeprecatedClass {
    Class newImpl();
}

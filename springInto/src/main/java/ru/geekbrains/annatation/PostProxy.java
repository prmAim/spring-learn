package main.java.ru.geekbrains.annatation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Собственная аннатация для работы после создания Proxy
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PostProxy {
}

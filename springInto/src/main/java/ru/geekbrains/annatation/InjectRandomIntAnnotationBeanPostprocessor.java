package main.java.ru.geekbrains.annatation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Цель: найти все bean в contextSpring и научить их пользоваться аннотацией @InjectRandomInt
 */
public class InjectRandomIntAnnotationBeanPostprocessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // Поиск всех полей у каждого бина
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            // У каждого поля пытаемся достать аннатоцию @InjectRandomInt, если она есть, то пишем логику
            InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();
                Random random = new Random();
                // Генерация случайного числа
                int i = min + random.nextInt(max - min);
                // Возврат полученного случайного числа обратно в поле, которое нашли в Bean
                field.setAccessible(true);
                // так не стоит делалть, так как нужно добавить обработку исключение
                //  field.setInt(i);

                // Встроенная обертка Exception, где field - где присваиваем знаяения, bean - для какого объекта, i - какое значние
                ReflectionUtils.setField(field, bean, i);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}

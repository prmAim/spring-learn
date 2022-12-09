package main.java.ru.geekbrains.annatation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Цель: найти замерить выполнение метода каждого bean, где есть аннатация @Profiling и включен режима профилирования [ProfillingControllerMBean] через программу VisualVM
 */
public class ProfilingHandlerbeanPostProcessor implements BeanPostProcessor {
    // Храним <название bean> и его <оригинальный класс>.
    private Map<String, Class> map;
    // Управление профилированием
    private ProfillingController controller;

    public ProfilingHandlerbeanPostProcessor() throws Exception {
        this.map = new HashMap<>();
        this.controller = new ProfillingController(true);

        // Добавялем, что бы найти в JSMConsul (управление значение в RinTime) через программу VisualVM
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        // profiling - имя домена (папка), name - имя поля, controller - название
        beanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // Получаем класс bean
        Class<?> beanClass = bean.getClass();
        // Если у класса стоит аннатация @Profiling, то запоминаем: <название bean> и его <оригинальный класс>.
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // Получаем <оригинальный класс bean>.
        Class beanClass = map.get(beanName);
        // Если у нас в map есть <название bean>, то значит стояла аннатация @Profiling
        if (beanClass != null) {
            // Создает объект из нового класса, которые СГЕНЕРИТ он сам "НА ЛЕТУ".
            // beanClass.getClassLoader() = Это абстрактный класс, ответственный за загрузку типов. По имени класса или интерфейса он находит и загружает в память данные, которые составляют определение типа.
            // список нтерфейсов класса, которые создан на лету
            // попадание во всю логику методов класса, которые создан на лету
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (controller.isEnabled()) {
                        System.out.println("Начала профилирования!");
                        long beforeTime = System.nanoTime();
                        // запуск оригинального метода с его параметрами
                        Object retVal = method.invoke(bean, args);
                        long afterTime = System.nanoTime();
                        System.out.println("Время выполнения: " + (afterTime - beforeTime));
                        System.out.println("Окончание профилирования!");
                        return retVal;
                    } else {
                        return method.invoke(bean, args);
                    }
                }
            });
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}

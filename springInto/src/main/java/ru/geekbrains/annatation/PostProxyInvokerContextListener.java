package main.java.ru.geekbrains.annatation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;

/**
 * Цель:
 */
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent>{
    // так как не можем получить <оригинальный класс> на этом этапе Phase3, так как тут уже могут быть proxyClass, а не сам  <оригинальный класс>.
    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        // вытащим все именна bean
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            // оригинальное название класс, которое прописано в SpringContext
            String originalClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> originalClass = Class.forName(originalClassName);
                Method[] originMethods = originalClass.getMethods();
                for (Method originMethod : originMethods) {
                    // Если у класса стоит аннатация @PostProxy, то выполняем логину...
                    if (originMethod.isAnnotationPresent(PostProxy.class)) {
                        // так как тут нахродяться уже не <оригинальный класс>, а его proxy, то нам нужно вытащить его <прокси-класс> [beanCurrent]
                        // при этом у  <оригинальный класс> и <прокси-класс> сигнатура и название методов одинаково
                        Object currentBean = context.getBean(name);
                        Method currentMethod = currentBean.getClass().getMethod(originMethod.getName(), originMethod.getParameterTypes());
                        // запускаем метод именно <прокси-класс>
                        currentMethod.invoke(currentBean);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

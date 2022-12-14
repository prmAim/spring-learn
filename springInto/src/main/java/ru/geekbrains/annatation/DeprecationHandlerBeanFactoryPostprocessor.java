package main.java.ru.geekbrains.annatation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Цель: создания bean <QouterImpl> заменить на свежую версию <QouterNewImpl>
 */
public class DeprecationHandlerBeanFactoryPostprocessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // дай мне все именна bean
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            // получаем из id =>  BeanDefinition
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            // получаем имя класса
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> beanClass = Class.forName(beanClassName);
                // поиск нашей аннатации @DeprecatedClass
                DeprecatedClass annotationPresent = beanClass.getAnnotation(DeprecatedClass.class);
                if (annotationPresent != null){
                    // Присваеиваем новое название beanDefinition на название аннатации из @DeprecatedClass(newImpl)
                    beanDefinition.setBeanClassName(annotationPresent.newImpl().getName());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}

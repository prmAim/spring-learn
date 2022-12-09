package main.java.ru.geekbrains.annatation;

/**
 * Создаем методы, которые хотим что бы были доступны через JMSConsul [программа VisualVM]
 */
public interface ProfillingControllerMBean {
    /**
     * Вкл/выкл режима профилирования
     * @param enabled
     */
    void setEnabled(boolean enabled);
}

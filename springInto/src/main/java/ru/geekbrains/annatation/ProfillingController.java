package main.java.ru.geekbrains.annatation;

/**
 * Класс для управления <профилирования> [@Profiling]: вкл/выкл через JMSConsul [программа VisualVM]
 */
public class ProfillingController implements ProfillingControllerMBean{

    private boolean enabled;

    public ProfillingController(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }
}

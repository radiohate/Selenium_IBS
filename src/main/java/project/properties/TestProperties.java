package project.properties;


import org.junit.jupiter.api.Assertions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {
    private final Properties properties = new Properties();
    private static TestProperties instance = null;

    private TestProperties() {
        try (FileInputStream input = new FileInputStream("src/test/resources/environment.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Properties getProperties() {
        return properties;
    }

    private static TestProperties getInstance() {
        if (instance == null) {
            instance = new TestProperties();
        }
        return instance;
    }

    /**
     * Получить значение по ключу из файла app.properties
     *
     * @param key ключ
     * @return значение
     */
    public static String getProperty(String key) {
        String value = getInstance().getProperties().getProperty(key);
        String errorMsg = String.format("Значение по ключу %s не найдено в app.properties", key);
        Assertions.assertNotNull(errorMsg, value);
        return value;
    }
}

package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyProvider {
    private final Properties properties;
    private static final String fileName = "config.properties";

    private static PropertyProvider instance;

    public PropertyProvider() {
        properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Configuration file not found");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static PropertyProvider getInstance(){
        if (instance == null) {
            return new PropertyProvider();
        }

        return instance;
    }

    public String getProperty(String propertyName) {
        String value = properties.getProperty(propertyName);
        return checkPropertyForNull(value, "Cannot find the property " + propertyName);
    }

    private <T> T checkPropertyForNull(T value, String message) {
        if (value == null) {
            throw new RuntimeException(message);
        }

        return value;
    }

}

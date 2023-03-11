package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {
    
    private final Properties mainProperties = new Properties();

    public Property(String path) {
        try {
            FileInputStream file = new FileInputStream(path);
            mainProperties.load(file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String name) {
        return this.mainProperties.getProperty(name);
    }
}

package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The Property class is used to read server properties form file *.properties
 * and enable to read them.
 * 
 * @author Arkadiusz Orzel
 * 
 */
public class Property {

    private final Properties mainProperties = new Properties();

    /**
     * The Property constructor is used to read the properties file.
     * 
     *
     * @param path Path to the file
     *
     */
    public Property(String path) {
        try {
            FileInputStream file = new FileInputStream(path);
            mainProperties.load(file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The getProperty function returns the value of a property from the
     * mainProperties object.
     * 
     *
     * @param name Get the property value from the properties file
     *
     * @return The value of the property with the given name
     *
     */
    public String getProperty(String name) {
        return this.mainProperties.getProperty(name);
    }
}

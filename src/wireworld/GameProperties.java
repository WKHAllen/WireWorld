package wireworld;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Will Allen
 */
public class GameProperties {

    private final String filename;
    private final Properties prop;

    public GameProperties(String filename) throws IOException {
        this.filename = filename;
        this.prop = GameProperties.readPropertiesFile(filename);
    }

    private static Properties readPropertiesFile(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        Properties prop = new Properties();
        prop.load(fis);
        fis.close();
        return prop;
    }

    public String getFilename() {
        return this.filename;
    }

    public Properties getProp() {
        return this.prop;
    }

    public String get(String key) {
        return this.prop.getProperty(key);
    }

    public void set(String key, String value) {
        this.prop.setProperty(key, value);
    }

}

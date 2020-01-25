package wireworld;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Will Allen
 */
public class Settings {

    private final String filename;
    private String comment;
    private final Properties prop;

    public Settings(String filename) throws IOException {
        this(filename, null);
    }
    
    public Settings(String filename, String comment) throws IOException {
        this.filename = filename;
        this.comment = comment;
        this.prop = Settings.readPropertiesFile(filename);
    }

    private static Properties readPropertiesFile(String filename) throws IOException {
        File propFile = new File(filename);
        propFile.createNewFile();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
    
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String get(String key) {
        return this.prop.getProperty(key);
    }

    public void set(String key, String value) throws IOException {
        this.prop.setProperty(key, value);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(this.filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.prop.store(fos, this.comment);
        fos.close();
    }

}

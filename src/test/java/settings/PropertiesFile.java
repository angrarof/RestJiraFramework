package settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {
    private Properties properties = new Properties();
    private FileInputStream file;

    public PropertiesFile(){
        loadProperties();
    }

    public void loadProperties(){
        String projectLocation = System.getProperty("user.dir");
        try {
            file = new FileInputStream(projectLocation+"/src/test/java/settings/config.properties");
            properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key).toLowerCase();
    }
}

package nyansa.exercise.urlhit.config;

import nyansa.exercise.urlhit.exception.ConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ConfigurationManager {
    public Properties parseConfiguration(String configFile) throws ConfigurationException {
        Properties prop = new Properties();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(configFile);
            if (resource != null) {
                prop.load(new FileInputStream(new File(resource.getFile())));
            } else {
                throw new ConfigurationException("file not found! Exception occurred! ");
            }
        } catch (IOException ioe) {
            throw new ConfigurationException("exception occurred while reading the file! ", ioe);
        }
        return prop;
    }
}

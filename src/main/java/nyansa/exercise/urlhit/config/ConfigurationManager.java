package nyansa.exercise.urlhit.config;

import nyansa.exercise.urlhit.exception.ConfigurationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigurationManager {
    public Properties parseConfiguration(String configFile) throws ConfigurationException {
        Properties prop = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(configFile);
            if (is != null) {
                InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader);
                for (String line; (line = reader.readLine()) != null; ) {
                    String[] split = line.split("=");
                    prop.put(split[0], split[1]);
                }
            } else {
                throw new ConfigurationException("file not found! Exception occurred! ");
            }

        } catch (IOException ioe) {
            throw new ConfigurationException("exception occurred while reading the file! ", ioe);
        }
        return prop;
    }
}

package nyansa.exercise.urlhit;

import lombok.extern.log4j.Log4j2;
import nyansa.exercise.urlhit.config.ConfigurationManager;
import nyansa.exercise.urlhit.exception.ConfigurationException;
import nyansa.exercise.urlhit.process.FileProcessor;

import java.util.Properties;

@Log4j2
public class Driver {
    public static void main(String[] args) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        try {
            Properties properties = configurationManager.parseConfiguration("config.properties");
            log.debug("configuration properties" + properties);
            new FileProcessor(args[0], properties).process();
        } catch (ConfigurationException e) {
            log.error("exception occurred while initializing the properties", e);
            System.exit(-1);
        }
    }

}

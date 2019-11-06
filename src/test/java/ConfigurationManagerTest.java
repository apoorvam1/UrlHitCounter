import nyansa.exercise.urlhit.config.ConfigurationManager;
import nyansa.exercise.urlhit.exception.ConfigurationException;
import org.junit.Assert;
import org.junit.Test;


import java.util.Properties;

public class ConfigurationManagerTest {

    @Test
    public void test_ParseConfiguration() throws ConfigurationException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        Properties properties = configurationManager.parseConfiguration("test-config.properties");
        Assert.assertNotNull(properties);
        Assert.assertEquals("./src/main/resources/temp/", properties.getProperty("temppath"));
    }

    @Test(expected = ConfigurationException.class)
    public void test_ParseConfigurationWithInvalidFile() throws ConfigurationException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.parseConfiguration("test-config-invalid.properties");
    }
}

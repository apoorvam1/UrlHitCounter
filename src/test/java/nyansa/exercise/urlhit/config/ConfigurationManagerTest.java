package nyansa.exercise.urlhit.config;

import nyansa.exercise.urlhit.exception.ConfigurationException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManagerTest {

    @Test
    public void test_ParseConfiguration() throws ConfigurationException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        Properties properties = configurationManager.parseConfiguration("test-config.properties");
        Assert.assertNotNull(properties);
        Assert.assertEquals(2, properties.size());
    }

    @Test(expected = ConfigurationException.class)
    public void test_ParseConfigurationWithInvalidFile() throws ConfigurationException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.parseConfiguration("test-config-invalid.properties");
    }
}

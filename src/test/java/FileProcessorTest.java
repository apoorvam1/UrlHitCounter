import org.junit.Assert;
import org.junit.Test;
import nyansa.exercise.urlhit.config.ConfigurationManager;
import nyansa.exercise.urlhit.exception.ConfigurationException;
import nyansa.exercise.urlhit.process.FileProcessor;

import java.util.Properties;

public class FileProcessorTest {

    private FileProcessor init(String filePath) throws ConfigurationException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        Properties properties = configurationManager.parseConfiguration("test-config.properties");
        FileProcessor fp = new FileProcessor(filePath, properties);
        return fp;
    }

    @Test
    public void Test_ValidInput() throws ConfigurationException {
        FileProcessor fp = init("./src/test/resources/input.txt");
        fp.process();
        Assert.assertTrue(true);
    }

    @Test
    public void Test_InputWithEmptyLines() throws ConfigurationException {
        FileProcessor fp = init("./src/test/resources/input-emptylines.txt");
        fp.process();
        Assert.assertTrue(true);
    }

    @Test
    public void Test_InputWithEmptyDate() throws ConfigurationException {
        FileProcessor fp = init("./src/test/resources/input-emptydate.txt");
        fp.process();
        Assert.assertTrue(true);
    }

    @Test
    public void Test_InputWithEmptyUrl() throws ConfigurationException {
        FileProcessor fp = init("./src/test/resources/input-emptyurl.txt");
        fp.process();
        Assert.assertTrue(true);
    }
}

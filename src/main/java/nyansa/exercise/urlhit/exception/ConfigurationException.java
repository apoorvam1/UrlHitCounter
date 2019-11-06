package nyansa.exercise.urlhit.exception;

public class ConfigurationException extends Exception {
    public ConfigurationException(String s, Exception e) {
        super(s, e);
    }

    public ConfigurationException(String s) {
        super(s);
    }

}

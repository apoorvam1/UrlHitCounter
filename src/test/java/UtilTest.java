import org.junit.Assert;
import org.junit.Test;
import nyansa.exercise.urlhit.utils.Util;

public class UtilTest {

    @Test
    public void Test_ValidGMTConversion() {
        String gmtDate = Util.getGMTDate("1407478021");
        Assert.assertEquals("08-08-2014 GMT", gmtDate);
    }

    @Test
    public void Test_InvalidGMTConversion() {
        String gmtDate = Util.getGMTDate("");
        Assert.assertEquals("", gmtDate);
    }
}

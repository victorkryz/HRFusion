package victor.kryz.hrfusion;

/**
 * HRFusion (unit tests)
 *
 * @author Victor Kryzhanivskyi
 */


import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Locale;
import victor.kryz.hrfusion.app.HRFusion;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LocateGettingTest {

    @Test
    public void test() throws Exception
    {
        HRFusion.LocalesStock stock =
                    HRFusion.getApplication().
                                    getLocalesStock();
        assertNotNull(stock);

        checkLocale(stock, "CA");
        checkLocale(stock, "AU");
        checkLocale(stock, "BE");
        checkLocale(stock, "CH");
        checkLocale(stock, "US");
    }

    @Test
    public void testUK() throws Exception
    {
        HRFusion.LocalesStock stock =
                HRFusion.getApplication().
                        getLocalesStock();
        assertNotNull(stock);

        checkLocale(stock, "UK");
    }

    void checkLocale(HRFusion.LocalesStock stock, String strCountry)
    {
        Locale locale = stock.getLocale(strCountry);
        assertNotNull(locale);
        assertEquals(locale.getCountry(), strCountry);
    }
}

package victor.kryz.hrfusion.entities;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import victor.kryz.hrfusion.hrdb.HrItem;
import victor.kryz.hrfusion.jni.PocoException;

import static org.junit.Assert.assertEquals;

/**
 * HRFusion (unit tests)
 *
 * @author Victor Kryzhanivskyi
 */

@RunWith(AndroidJUnit4.class)
public class ReadCountriesTest extends JUnitTestBase {

    String mRegion;

    @Before
    public void setUpRegion() {
        mRegion = "2"; // Americas
    }

    @Test
    public void test() throws PocoException
    {
        List<HrItem> items = getSession().getCountries(mRegion);
        assertEquals(items.size(), 5);

        HRItemCriteria<HrItem> criteria =
                new HRItemCriteria<HrItem>() {
                    @Override
                    public void assertItem(final HrItem item, String strPattrren) {
                        assertEquals(item.getName(), strPattrren);
                    };
                };

        checkItemForCriteria(items, 0, "Argentina", criteria);
        checkItemForCriteria(items, 1, "Brazil", criteria);
        checkItemForCriteria(items, 2, "Canada", criteria);
        checkItemForCriteria(items, 3, "Mexico", criteria);
        checkItemForCriteria(items, 4, "United States of America", criteria);
    }
}

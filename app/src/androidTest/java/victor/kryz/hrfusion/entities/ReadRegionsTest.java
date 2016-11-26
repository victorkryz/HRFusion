package victor.kryz.hrfusion.entities;

import android.support.test.runner.AndroidJUnit4;
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
public class ReadRegionsTest extends JUnitTestBase
{
    @Test
    public void test() throws PocoException
    {
        List<HrItem> items = getSession().getRegions();
        assertEquals(items.size(), 4);

        HRItemCriteria<HrItem> criteria =
                new HRItemCriteria<HrItem>() {
            @Override
            public void assertItem(final HrItem item, String strPattrren) {
                assertEquals(item.getName(), strPattrren);
            };
        };

        checkItemForCriteria(items, 0, "Americas", criteria);
        checkItemForCriteria(items, 1, "Asia", criteria);
        checkItemForCriteria(items, 2, "Europe", criteria);
        checkItemForCriteria(items, 3, "Middle East and Africa", criteria);
    }
}

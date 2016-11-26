package victor.kryz.hrfusion.entities;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import victor.kryz.hrfusion.hrdb.Location;
import victor.kryz.hrfusion.jni.PocoException;

import static org.junit.Assert.assertEquals;

/**
 * HRFusion (unit tests)
 *
 * @author Victor Kryzhanivskyi
 */

public class ReadLocationsTest extends JUnitTestBase {

    String mCountry;

    @Before
    public void setUpCountry() {
        mCountry = "UK";
    }

    @Test
    public void test() throws PocoException
    {
        List<Location> items = getSession().getLocations(mCountry);
        assertEquals(items.size(), 3);

        HRItemCriteria<Location> criteria =
                new HRItemCriteria<Location>() {
                    @Override
                    public void assertItem(final Location item, String strPattrren) {
                        assertEquals(item.getCity(), strPattrren);
                    };
                };

        checkItemForCriteria(items, 0, "London", criteria);
        checkItemForCriteria(items, 1, "Oxford", criteria);
        checkItemForCriteria(items, 2, "Stretford", criteria);
    }
}

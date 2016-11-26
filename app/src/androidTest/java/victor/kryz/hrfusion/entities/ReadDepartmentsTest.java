package victor.kryz.hrfusion.entities;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import victor.kryz.hrfusion.hrdb.Department;
import victor.kryz.hrfusion.jni.PocoException;

import static org.junit.Assert.assertEquals;

/**
 * HRFusion (unit tests)
 *
 * @author Victor Kryzhanivskyi
 */


@RunWith(AndroidJUnit4.class)
public class ReadDepartmentsTest extends JUnitTestBase {

    String mLocation;

    @Before
    public void setUpLocation() {
        mLocation = "1400"; // IT
    }

    @Test
    public void test() throws PocoException
    {
        List<Department> items = getSession().getDepartments(mLocation);
        assertEquals(items.size(), 1);

        checkItemForCriteria(items, 0, "IT",
                            new HRItemCriteria<Department>() {
                                @Override
                                public void assertItem(final Department item, String strPattrren) {
                                    assertEquals(item.getName(), strPattrren);
                                }
                            }
                        );
    }
}

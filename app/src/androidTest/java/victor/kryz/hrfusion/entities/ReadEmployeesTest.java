package victor.kryz.hrfusion.entities;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import victor.kryz.hrfusion.hrdb.Employee;
import victor.kryz.hrfusion.jni.PocoException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * HRFusion (unit tests)
 *
 * @author Victor Kryzhanivskyi
 */


@RunWith(AndroidJUnit4.class)
public class ReadEmployeesTest extends JUnitTestBase
{
    String mDepartment;

    @Before
    public void setUpDepartment() {
        mDepartment = "50"; // Shipping
    }

    @Test
    public void test() throws PocoException
    {
        List<Employee> items = getSession().getEmployees(mDepartment);
        assertEquals(items.size(), 45);

        Employee item0 = items.get(0);
        assertEquals("Adam", item0.getName());
        assertEquals("Fripp", item0.getLastName());
        assertEquals("Stock Manager", item0.getJobTitle());
        assertTrue(item0.isMngr());

        Employee item44 = items.get(44);
        assertEquals("Winston", item44.getName());
        assertEquals("Taylor", item44.getLastName());
        assertEquals("Shipping Clerk", item44.getJobTitle());
        assertFalse(item44.isMngr());
    }
}

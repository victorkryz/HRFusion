package victor.kryz.hrfusion.entities;

/**
 * HRFusion (unit tests)
 *
 * @author Victor Kryzhanivskyi
 */

import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import victor.kryz.hrfusion.hrdb.JobStage;
import victor.kryz.hrfusion.jni.PocoException;

import static org.junit.Assert.assertEquals;



@RunWith(AndroidJUnit4.class)
public class ReadJobHistoryTest extends JUnitTestBase
{
    String mEmployeeId;

    @Before
    public void setUpEmployee() {
        mEmployeeId = "101";
    }

    @Test
    public void test() throws PocoException
    {
        List<JobStage> items = getSession().getJobHistory(mEmployeeId);
        assertEquals(2, items.size());

        JobStage js0 = items.get(0);
        assertEquals("AC_MGR", js0.getId());
        assertEquals("Accounting", js0.getDepartmentName());

        JobStage js1 = items.get(1);
        assertEquals("AC_ACCOUNT", js1.getId());
        assertEquals("Accounting", js1.getDepartmentName());
    }
}

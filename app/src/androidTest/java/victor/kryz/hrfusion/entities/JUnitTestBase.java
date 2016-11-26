package victor.kryz.hrfusion.entities;

import android.support.annotation.NonNull;
import android.util.Log;
import java.util.List;
import victor.kryz.hrfusion.hrdb.HrItem;
import victor.kryz.hrfusion.hrdb.Session;
import static org.junit.Assert.assertNotNull;

/**
 * HRFusion (unit tests)
 *
 * @author Victor Kryzhanivskyi
 */

public class JUnitTestBase {

    static final String TRACE_TAG = JUnitTestBase.class.getSimpleName();

    public Session getSession()
    {
        if ( null == ReadHREntitiesFixture.mSession )
        {
            try{
                ReadHREntitiesFixture.init();
            }
            catch (Throwable e) {
                Log.e(TRACE_TAG,  e.getMessage());
            }

            assertNotNull(ReadHREntitiesFixture.mSession);
        }

        return ReadHREntitiesFixture.mSession;
    }

    protected <T extends HrItem> void checkItemForCriteria(@NonNull final List<T> items,
                                        int itemOrder, @NonNull String strPattern, HRItemCriteria<T> criteria)
    {
        assert items != null;
        assert criteria != null;
        final T item = items.get(itemOrder);
        criteria.assertItem(item, strPattern);
    }
}

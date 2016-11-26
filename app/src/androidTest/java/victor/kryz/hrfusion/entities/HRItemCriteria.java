package victor.kryz.hrfusion.entities;

import android.support.annotation.NonNull;
import victor.kryz.hrfusion.hrdb.HrItem;

/**
 * HRFusion (unit tests)
 *
 * @author Victor Kryzhanivskyi
 */

public interface HRItemCriteria<T extends HrItem> {
    void assertItem(final @NonNull T item, String strPattern);
}

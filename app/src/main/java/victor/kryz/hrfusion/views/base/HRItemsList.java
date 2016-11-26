package victor.kryz.hrfusion.views.base;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.support.annotation.NonNull;

import java.util.List;

import victor.kryz.hrfusion.hrdb.HrItem;

public interface HRItemsList<T extends HrItem> {
     List<T> getItemsList();
     void setItemsList(@NonNull List<T> list);
}

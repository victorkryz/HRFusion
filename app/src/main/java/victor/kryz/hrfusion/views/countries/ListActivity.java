package victor.kryz.hrfusion.views.countries;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import victor.kryz.hrfusion.hrdb.HrItem;
import victor.kryz.hrfusion.hrdb.Session;
import victor.kryz.hrfusion.hrdb.sqlt.SessionFactory;
import victor.kryz.hrfusion.jni.PocoException;

public class ListActivity extends victor.kryz.hrfusion.views.base.ListActivity
{
    @Override
    protected void setupRecyclerView(@NonNull RecyclerView recyclerView)
    {
        List<HrItem> items = new ArrayList<HrItem>();
        recyclerView.setAdapter(new ViewAdapter(this, items));
        refreshAsynch();
    }

    @Override
    protected  void populateItemsList() throws PocoException
    {
        Session ses = SessionFactory.getSession();
        List<HrItem> items = ses.getCountries(mItemId);
        updateItemsList(items);
    };

}

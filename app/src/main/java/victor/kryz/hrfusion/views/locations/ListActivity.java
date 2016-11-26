package victor.kryz.hrfusion.views.locations;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import victor.kryz.hrfusion.hrdb.Location;
import victor.kryz.hrfusion.hrdb.Session;
import victor.kryz.hrfusion.hrdb.sqlt.SessionFactory;
import victor.kryz.hrfusion.jni.PocoException;

public class ListActivity extends victor.kryz.hrfusion.views.base.ListActivity
{
    @Override
    protected void setupRecyclerView(@NonNull RecyclerView recyclerView)
    {
        List<Location> items = new ArrayList<Location>();
        recyclerView.setAdapter(new ViewAdapter(this, items));
        refreshAsynch();
    }

    @Override
    protected  void populateItemsList() throws PocoException
    {
        Session ses = SessionFactory.getSession();
        List<Location> items = ses.getLocations(mItemId);
        updateItemsList(items);
    };
}

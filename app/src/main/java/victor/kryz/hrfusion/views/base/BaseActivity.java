package victor.kryz.hrfusion.views.base;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.List;
import java.util.Locale;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.app.HRFusion;
import victor.kryz.hrfusion.app.LibsLoader;
import victor.kryz.hrfusion.hrdb.HrItem;
import victor.kryz.hrfusion.hrdb.Location;
import victor.kryz.hrfusion.jni.PocoException;
import victor.kryz.hrfusion.views.Utils;


/**
 * BaseActivity - base class for all entity-list activities
 */
public abstract class BaseActivity extends AppCompatActivity
{
    protected static String LOG_TAG = BaseActivity.class.getSimpleName();

    static {
        LibsLoader.load();
    }

    protected String mItemId;
    protected Location mLocation;
    protected ProgressDialog mProgressDlg;

    /**
     * HrItemsSelector class - intended for fetching
     *                         of entities from the database in non-UI thread.
     *                         After that, reflects them in UI-thread.
     */
    protected class HrItemsSelector implements Runnable
    {
        final String itemId;
        public HrItemsSelector(String itemId) {
            this.itemId = itemId;
        }

        @Override
        public void run()
        {
            try
            {
                populateItemsList();

                getActivity().runOnUiThread(new Runnable() {
                    public void run()
                    {
                        try
                        {
                            RecyclerView rv = getRecyclerView();
                            RecyclerView.Adapter adapter = rv.getAdapter();
                            adapter.notifyDataSetChanged();
                            HRItemsList<HrItem> itemsList  = (HRItemsList<HrItem>)adapter;
                            updateTitle(itemsList);
                      }
                        finally {
                            if ( null != mProgressDlg )
                                mProgressDlg.dismiss();
                        }
                     }
                });
            }
            catch (PocoException e)
            {
                Log.e(LOG_TAG, e.getMessage());
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( null != savedInstanceState )
            initInstanceState(savedInstanceState);
        else {
            Bundle bundle = getIntent().getExtras();
            initInstanceState(bundle);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( mProgressDlg != null) {
             mProgressDlg.dismiss();
             mProgressDlg = null;
        }
    }

    /**
     * Launches procedure of fetching data
     * and reflecting them in asynchronous way
     */
    public void refreshAsynch()
    {
        Thread th = new Thread(new HrItemsSelector(mItemId));
        th.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(LOG_TAG, "onSaveInstanceState() method called");

        saveInstanceState(savedInstanceState);
    }

    abstract protected void updateTitle(@NonNull  HRItemsList<HrItem> itemsList);

    /**
     * Gets "Locale", basing on the
     * contextual "Location" entity.
     *
     *
     * @return - "Locale" instance or
     *            "null" if not found;
     *
     * Note: db-entities "Countries", "Departments", "Employees", etc.
     * have the "Location" entity as a parent in their hierarchy.
     */
    protected  Locale getContextLocale()
    {
        Locale locale = null;
        Location location = getContextLocation();
        if ( null != location )
            locale =  HRFusion.getApplication().
                                    getLocalesStock().
                                        getLocale(location.getCountryId());
        return locale;
    }

    protected  Location getContextLocation()
    {
        if ( null == mLocation )
            mLocation = (Location)getIntent().
                                    getSerializableExtra(ArgsKeys.CURRENT_LOCATION);
        return mLocation;
    }

    /**
     * Formats salary value according to contextual "Location"
     * @param salary value
     * @return string, represented given value
     */
    public String salaryToString(Double salary) {
        return Utils.salaryToString(salary, getContextLocale());
    }

    protected AppCompatActivity getActivity() {
        return this;
    }

    /**
     * Sets/updates a new list of entities.
     * @param items - list of entities
     */
    protected void updateItemsList(@NonNull List<? extends HrItem> items)
    {
        RecyclerView rv = getRecyclerView();
        HRItemsList<HrItem> itemsList  = (HRItemsList<HrItem>)rv.getAdapter();
        itemsList.setItemsList((List<HrItem>)items);
    }

    protected void saveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(ArgsKeys.ITEM_ID, mItemId);
    }

    protected void initInstanceState(Bundle savedInstanceState) {
        mItemId = savedInstanceState.getString(ArgsKeys.ITEM_ID);
    }

    protected abstract void setupRecyclerView(@NonNull RecyclerView recyclerView);
    protected abstract RecyclerView getRecyclerView();
    protected  abstract void populateItemsList() throws PocoException;
}

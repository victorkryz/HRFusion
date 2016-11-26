package victor.kryz.hrfusion.views.base;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.app.LibsLoader;

/**
 * Base activity for all detailed info activities
 */
public class DetailsActivity extends AppCompatActivity {

    protected static String LOG_TAG = DetailsActivity.class.getSimpleName();

    static {
        LibsLoader.load();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle args = getIntent().getExtras();
        final int layoutId = args.getInt(ArgsKeys.ITEM_LAYOUT_ID);
        final String strClass = args.getString(ArgsKeys.DETAILED_FRAGMENT_CLASS);

        Class<? extends Fragment> detailedFragmentClass = null;

        try {
            detailedFragmentClass = (Class<? extends Fragment>)Class.forName(strClass);
        } catch (ClassNotFoundException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        if (null == detailedFragmentClass)
            finish();

        setContentView(layoutId);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null)
        {
            Fragment fragment = null;

            try {
                fragment = (Fragment)(detailedFragmentClass.newInstance());
            } catch (InstantiationException e) {
                Log.e(LOG_TAG, e.getMessage());
            } catch (IllegalAccessException e) {
                Log.e(LOG_TAG, e.getMessage());
            }

            if ( null == fragment )
                finish();
            else
            {
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.item_detail_container, fragment)
                        .commit();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_down);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(LOG_TAG, "onSaveInstanceState() method called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(LOG_TAG, "onRestoreInstanceState() method called");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, ListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTitle(String strTitle)
    {
        CollapsingToolbarLayout appBarLayout =
                (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(strTitle);
        }
    }
}

package victor.kryz.hrfusion.views.employees;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.HrItem;
import victor.kryz.hrfusion.hrdb.JobStage;
import victor.kryz.hrfusion.hrdb.Session;
import victor.kryz.hrfusion.hrdb.sqlt.SessionFactory;
import victor.kryz.hrfusion.jni.PocoException;
import victor.kryz.hrfusion.views.base.*;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends BaseActivity {

    private Toolbar toolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        Bundle mArgs;
        ArrayList<victor.kryz.hrfusion.views.base.DetailsFragment> m_TabFragments =
                new ArrayList<victor.kryz.hrfusion.views.base.DetailsFragment>();

        public ViewPagerAdapter(FragmentManager fm, Bundle args)
        {
            super(fm);
            mArgs = args;

            {
                DetailsFragment fg = new DetailsFragment();
                fg.setArguments(mArgs);
                m_TabFragments.add(fg);
            }

            {
                JobHistoryFragment fg = new JobHistoryFragment();
                fg.setArguments(mArgs);
                m_TabFragments.add(fg);
            }

        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment fg = null;
            if ( position < m_TabFragments.size() )
                fg = m_TabFragments.get(position);
            return fg;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            String strTitle = null;

            switch (position)
            {
                case 0:
                {
                    strTitle = m_TabFragments.get(0).getTitle(getActivity());
                    break;
                }
                case 1:
                {
                    strTitle = m_TabFragments.get(1).getTitle(getActivity());
                    break;
                }
            }
            return strTitle;
        }


        @Override
        public int getCount() {
            return m_TabFragments.size();
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle args = getIntent().getExtras();
        mItemId = args.getString(ArgsKeys.ITEM_ID);

        setContentView(R.layout.employee_detail_extended);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.colorPrimaryDark));
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), args);
        mViewPager.setAdapter(mViewPagerAdapter);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void updateTitle(@NonNull HRItemsList<HrItem> itemsList) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, victor.kryz.hrfusion.views.base.ListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected RecyclerView getRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.item_list);
        assert recyclerView != null;
        return recyclerView;
    }


    @Override
    protected void setupRecyclerView(@NonNull RecyclerView recyclerView)
    {
        recyclerView.setAdapter(new DetaislViewAdapter(this, new ArrayList<JobStage>()));
        refreshAsynch();
    }

    @Override
    protected  void populateItemsList() throws PocoException
    {
        Session ses = SessionFactory.getSession();
        List<JobStage> items = ses.getJobHistory(mItemId);
        updateItemsList(items);
    };
}



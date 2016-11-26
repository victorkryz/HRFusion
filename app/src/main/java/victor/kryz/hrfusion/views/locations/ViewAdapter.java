package victor.kryz.hrfusion.views.locations;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.Location;
import victor.kryz.hrfusion.views.base.ArgsKeys;
import victor.kryz.hrfusion.views.base.DetailsActivity;
import victor.kryz.hrfusion.views.base.BaseViewAdapter;
import victor.kryz.hrfusion.views.base.ViewHolders;

public class ViewAdapter extends BaseViewAdapter<ViewHolders.BaseViewHolder, Location> {

    public ViewAdapter(AppCompatActivity activity, List<Location> items) {
        super(activity, ViewHolders.BaseViewHolder.class);
        mItems = items;
    }

    @Override
    public ViewHolders.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.locations_list_item, parent, false);
        return ViewHolders.createInstance(mActivity, mHolderClass, view);
    }

    @Override
    public void onBindViewHolder(final ViewHolders.BaseViewHolder holder, int position)
    {
        setCommonItemViews(holder,  position);
        setCommonClickHandlers(holder,  position);
    }

    @Override
    protected void onBeforeChilActivityStart(Context context, Intent intent, String itemId, int position) {
        promoteLocation(context, intent, position);
    }

    @Override
    protected void onBeforeDetailedActivityStart(Context context, Intent intent, String itemId, int position) {
        promoteLocation(context, intent, position);
    }

    @Override
    protected Class<victor.kryz.hrfusion.views.departments.ListActivity> getViewChildrenActivityClass() {
        return victor.kryz.hrfusion.views.departments.ListActivity.class;
    }

    protected Class<DetailsActivity> getItemDetailActivityClass() {
        return DetailsActivity.class;
    }
    protected Class<DetailsFragment> getItemDetailFragmentClass() {
        return DetailsFragment.class;
    }
    protected int getItemDetailLayoutId() {
        return R.layout.activity_item_detail;
    }

    private void promoteLocation(Context context, Intent intent, int position)
    {
        Location loc = mItems.get(position);
        if ( null != loc )
            intent.putExtra(ArgsKeys.CURRENT_LOCATION, loc);
    }
}

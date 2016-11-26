package victor.kryz.hrfusion.views.departments;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.Department;
import victor.kryz.hrfusion.views.base.ArgsKeys;
import victor.kryz.hrfusion.views.base.DetailsActivity;
import victor.kryz.hrfusion.views.base.DetailsFragment;
import victor.kryz.hrfusion.views.base.BaseViewAdapter;
import victor.kryz.hrfusion.views.base.ViewHolders;

public class ViewAdapter extends BaseViewAdapter<ViewHolders.BaseViewHolder, Department> {

    public ViewAdapter(AppCompatActivity activity, List<Department> items) {
        super(activity, ViewHolders.BaseViewHolder.class);
        mItems = items;
    }

    @Override
    public ViewHolders.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.departments_list_item, parent, false);
        return ViewHolders.createInstance(mActivity, mHolderClass, view);
    }

    @Override
    public void onBindViewHolder(final ViewHolders.BaseViewHolder holder, int position)
    {
        setCommonItemViews(holder,  position);
        setCommonClickHandlers(holder,  position);
    }

    protected void onBeforeChilActivityStart(Context context, Intent intent, String itemId, int position) {
        Activity activity = getActivity();
        Serializable location = activity.getIntent().getSerializableExtra(ArgsKeys.CURRENT_LOCATION);
        intent.putExtra(ArgsKeys.CURRENT_LOCATION, location);
    }

    @Override
    protected Class<victor.kryz.hrfusion.views.employees.ListActivity> getViewChildrenActivityClass() {
        return victor.kryz.hrfusion.views.employees.ListActivity.class;
    }
    protected Class<DetailsActivity> getItemDetailActivityClass() {
        return null;
    }
    protected Class<DetailsFragment> getItemDetailFragmentClass() {
        return null;
    }
    protected int getItemDetailLayoutId() {
        return 0;
    }
}

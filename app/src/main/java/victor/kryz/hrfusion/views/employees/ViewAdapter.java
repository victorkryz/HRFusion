package victor.kryz.hrfusion.views.employees;

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
import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.Employee;
import victor.kryz.hrfusion.views.base.ArgsKeys;
import victor.kryz.hrfusion.views.base.BaseViewAdapter;
import victor.kryz.hrfusion.views.base.ViewHolders;

public class ViewAdapter extends BaseViewAdapter<ViewHolders.BaseViewHolder, Employee> {

    public ViewAdapter(AppCompatActivity activity, List<Employee> items) {
        super(activity, ViewHolders.BaseViewHolder.class);
        mItems = items;
    }

    @Override
    public ViewHolders.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employees_list_item, parent, false);
        return ViewHolders.createInstance(mActivity, mHolderClass, view);
    }

    @Override
    public void onBindViewHolder(final ViewHolders.BaseViewHolder holder, int position)
    {
        setCommonItemViews(holder,  position);

        if ( null != holder.mItem)
        {
            Employee item = (Employee)holder.mItem;
            ImageView iconView = (ImageView)holder.mView.findViewById(R.id.iconEntity);
            if ( null != iconView)
                iconView.setImageResource(item.isMngr() ?
                                          R.drawable.ic_chef :R.drawable.ic_employee);
        }

        if ( holder.mDropDownView != null)
            holder.mDropDownView.setVisibility(View.INVISIBLE);

        setOnClickListener_ShowDetails(holder.mDetailsView, position);
    }

    @Override
    protected void onBeforeChilActivityStart(Context context, Intent intent, String itemId, int position) {
        promoteLocation(context, intent, position);
    }

    @Override
    protected void onBeforeDetailedActivityStart(Context context, Intent intent, String itemId, int position) {
        promoteLocation(context, intent, position);
    }

    private void promoteLocation(Context context, Intent intent, int position)
    {
        Activity activity = getActivity();
        Serializable location = activity.getIntent().getSerializableExtra(ArgsKeys.CURRENT_LOCATION);
        intent.putExtra(ArgsKeys.CURRENT_LOCATION, location);
    }

    @Override
    protected  <T extends Activity> Class<T> getViewChildrenActivityClass() {
        return null;
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
}

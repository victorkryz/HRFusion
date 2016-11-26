package victor.kryz.hrfusion.views.regions;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.HrItem;
import victor.kryz.hrfusion.views.base.DetailsActivity;
import victor.kryz.hrfusion.views.base.DetailsFragment;
import victor.kryz.hrfusion.views.base.BaseViewAdapter;
import victor.kryz.hrfusion.views.base.ViewHolders;

public class ViewAdapter extends BaseViewAdapter<ViewHolders.BaseViewHolder, HrItem> {

    public ViewAdapter(AppCompatActivity activity, List<HrItem> items) {
        super(activity, ViewHolders.BaseViewHolder.class);
        mItems = items;
    }

    @Override
    public ViewHolders.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.regions_list_item, parent, false);
        return ViewHolders.createInstance(mActivity, mHolderClass, view);
    }

    @Override
    public void onBindViewHolder(final ViewHolders.BaseViewHolder holder, int position)
    {
        setCommonItemViews(holder,  position);
        setCommonClickHandlers(holder,  position);
    }

    @Override
    protected  Class<victor.kryz.hrfusion.views.countries.ListActivity> getViewChildrenActivityClass(){
        return victor.kryz.hrfusion.views.countries.ListActivity.class;
    }

    @Override
    protected Class<DetailsActivity> getItemDetailActivityClass() {
        return null;
    }

    @Override
    protected Class<DetailsFragment> getItemDetailFragmentClass() {
        return DetailsFragment.class;
    }

    @Override
    protected int getItemDetailLayoutId() {
        return R.layout.activity_item_detail;
    }
}

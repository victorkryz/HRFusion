package victor.kryz.hrfusion.views.employees;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.JobStage;
import victor.kryz.hrfusion.views.Utils;
import victor.kryz.hrfusion.views.base.HRItemsList;
import victor.kryz.hrfusion.views.base.BaseActivity;

public class DetaislViewAdapter extends RecyclerView.Adapter<DetaislViewAdapter.ViewHolder>
                                implements HRItemsList<JobStage>
{
    private List<JobStage> mItems;
    private BaseActivity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }

    public DetaislViewAdapter(BaseActivity activity, List<JobStage> items) {
        mActivity = activity;
        mItems = items;
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_history_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final JobStage item = mItems.get(position);

        assert item != null;

        Utils.TextView tv = new Utils.TextView(holder.mView);

        String strPeriod = item.getStartDate() + "  -  " + item.getEndDate();
        tv.setText(R.id.period, strPeriod);
        tv.setText(R.id.department, item.getDepartmentName());
        tv.setText(R.id.jobTitle, item.getTitle());

        Double mins = item.getMinSalary();
        Double maxs = item.getMinSalary();

        String strSalaryRange = mActivity.salaryToString(mins) + "  -  " +
                                    mActivity.salaryToString(maxs);

        tv.setText(R.id.salaryRange, strSalaryRange);
    }

    @Override
    public int getItemCount() {
         return mItems.size();
    }

    @Override
    public List<JobStage> getItemsList() {
        return mItems;
    }
    @Override
    public void setItemsList(List<JobStage> list) {
        assert mItems != null;
        mItems = list;
    }
}

package victor.kryz.hrfusion.views.employees;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.Employee;
import victor.kryz.hrfusion.views.base.DetailsFragment;


public class JobHistoryFragment extends DetailsFragment<Employee>
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.employee_job_history, container, false);
        View recyclerView = getRecView(rootView);
        DetailsActivity activity = (DetailsActivity)getActivity();
        activity.setupRecyclerView((RecyclerView) recyclerView);
        return rootView;
    }

    public String getTitle(@NonNull Context context) {
        return context.getString(R.string.js_tab_tite);
    }

    protected RecyclerView getRecView(View rootView) {
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.item_list);
        assert recyclerView != null;
        return recyclerView;
    }
}

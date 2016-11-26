package victor.kryz.hrfusion.views.employees;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.Employee;
import victor.kryz.hrfusion.views.Utils;
import victor.kryz.hrfusion.views.base.BaseActivity;

public class DetailsFragment extends victor.kryz.hrfusion.views.base.DetailsFragment<Employee> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.employee_detail, container, false);
        assert rootView != null;

        BaseActivity activity = (BaseActivity)getActivity();

        CollapsingToolbarLayout appBarLayout =
                (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getTitle());
        }

        if (mItem != null)
        {
            Utils.TextView tv = new Utils.TextView(rootView);

            tv.setText(R.id.first_name, mItem.getName());
            tv.setText(R.id.first_name, mItem.getName());
            tv.setText(R.id.last_name, mItem.getLastName());
            tv.setText(R.id.email, mItem.getEmail());
            tv.setText(R.id.phone, mItem.getPhoneNumber());
            tv.setText(R.id.hire_date, mItem.getHireDate());
            tv.setText(R.id.jobTitle, mItem.getJobTitle());
            tv.setText(R.id.tvId, mItem.getId());

            int roleStrId = mItem.isMngr() ? R.string.manager_role : R.string.non_manager_role;
            tv.setText(R.id.role, getActivity().getResources().getString(roleStrId));

            String strPaymant = activity.salaryToString(mItem.getSalary());
            tv.setText(R.id.salary, strPaymant);

            Float pct = mItem.getComissionPct();
            tv.setText(R.id.comission, pct.toString());
        }

        return rootView;
    }

    public String getTitle(@NonNull Context context) {
        return context.getString(R.string.details_tab_title);
    }
}

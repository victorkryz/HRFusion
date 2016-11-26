package victor.kryz.hrfusion.views.locations;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.Location;
import victor.kryz.hrfusion.views.base.DetailsActivity;

public class DetailsFragment extends victor.kryz.hrfusion.views.base.DetailsFragment<Location> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.location_detail, container, false);

        if (mItem != null)
        {
            DetailsActivity activity  = (DetailsActivity)getActivity();
            activity.setTitle(mItem.getTitle());

            ((TextView) rootView.findViewById(R.id.city)).setText(mItem.getCity());
            ((TextView) rootView.findViewById(R.id.state)).setText(mItem.getStateProvince());
            ((TextView) rootView.findViewById(R.id.street_adress)).setText(mItem.getStreetAddress());
            ((TextView) rootView.findViewById(R.id.postal)).setText(mItem.getPostalCode());
        }

        return rootView;
    }

    @Override
    public String getTitle(@NonNull Context context){
        return "";
    }
}

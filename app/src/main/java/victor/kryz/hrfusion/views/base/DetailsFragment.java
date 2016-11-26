package victor.kryz.hrfusion.views.base;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.HrItem;

public abstract class DetailsFragment<HT extends HrItem> extends Fragment {

    protected HT mItem;

    public DetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args.containsKey(ArgsKeys.ITEM_CONTENT)) {

            mItem = (HT)args.getSerializable(ArgsKeys.ITEM_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.getId());
        }

        return rootView;
    }

    public abstract String getTitle(@NonNull Context context);
}

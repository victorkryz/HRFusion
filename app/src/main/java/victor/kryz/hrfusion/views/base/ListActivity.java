package victor.kryz.hrfusion.views.base;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.HrItem;


/**
 * ListActivity - base class for entities-list activities
 */
public abstract class ListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int listLayoutId = getListItemLayoutId();
        setContentView(listLayoutId);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        RecyclerView recyclerView = getRecyclerView();

        android.support.v7.widget.DividerItemDecoration dec =
        new DividerItemDecoration(recyclerView.getContext(),
                android.support.v7.widget.DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dec);

        setupRecyclerView(recyclerView);
    }

    protected RecyclerView getRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.item_list);
        assert recyclerView != null;
        return recyclerView;
    }

    protected void updateTitle(@NonNull HRItemsList<HrItem> itemsList)
    {
        String strNewTitle = getTitle() + " (" + itemsList.getItemsList().size() + ")";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle(strNewTitle);
    }

    protected  int getListItemLayoutId() {
        return R.layout.activity_item_list;
    }
}

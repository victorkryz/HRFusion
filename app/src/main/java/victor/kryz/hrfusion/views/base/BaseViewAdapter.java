package victor.kryz.hrfusion.views.base;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.HrItem;

/**
 * BaseViewAdapter - base class for "entity list" adapters,
 *                  that consolidates common functionality for all entities
 *
 * @param <T> - entity-specific ViewHolder
 * @param <HT> - entity class
 */
public abstract class BaseViewAdapter<T extends RecyclerView.ViewHolder, HT extends HrItem>
                                        extends RecyclerView.Adapter<T>
                                        implements HRItemsList<HT>
{

    protected List<HT> mItems;
    protected Class<T> mHolderClass;
    protected AppCompatActivity mActivity;

    public BaseViewAdapter(AppCompatActivity activity, Class<T> holderClass) {
        mActivity = activity;
        mHolderClass = holderClass;
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_content, parent, false);
        return ViewHolders.createInstance(mActivity, mHolderClass, view);
    }


    protected void startViewChildrenActivity(String itemId, int position)
    {
        Class<? extends Activity> clazz = getViewChildrenActivityClass();
        if ( null != clazz ) {

            Context context = getActivity();
            Intent intent = new Intent(context, getViewChildrenActivityClass());
            intent.putExtra(ArgsKeys.ITEM_ID, itemId);

            onBeforeChilActivityStart(context, intent, itemId, position);
            context.startActivity(intent);
            mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }


    protected void setCommonItemViews(final ViewHolders.BaseViewHolder holder, int position)
    {
        holder.mItem = mItems.get(position);
        holder.mDescrView.setText(mItems.get(position).getId());
        holder.mTitleView.setText(mItems.get(position).getTitle());
    }

    protected void setCommonClickHandlers(final ViewHolders.BaseViewHolder holder, int position)
    {
        if ( holder.mDetailsView != null)
            setOnClickListener_ShowDetails(holder.mDetailsView, position);
        if ( null != holder.mDropDownView )
            setOnClickListener_ListChildren(holder.mDropDownView, holder.mItem.getId(), position);
    }

    public List<HT> getItemsList() {
        return mItems;
    }
    public void setItemsList(@NonNull List<HT> list) {
        assert mItems != null;
        mItems = list;
    }

    @Override
    public abstract void onBindViewHolder(final T holder, int position);

    @Override
    public int getItemCount()
    {
        return mItems.size();
    };

    protected AppCompatActivity getActivity() {
        return mActivity;
    }


    protected void setOnClickListener_ShowDetails(View view, final int position) {
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HT item = mItems.get(position);

                        Class<? extends Activity> activityClass = getItemDetailActivityClass();
                        if ( null != activityClass )
                        {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, activityClass);
                            intent.putExtra(ArgsKeys.ITEM_CONTENT, item);
                            intent.putExtra(ArgsKeys.ITEM_ID, item.getId());
                            intent.putExtra(ArgsKeys.ITEM_LAYOUT_ID, getItemDetailLayoutId());
                            intent.putExtra(ArgsKeys.DETAILED_FRAGMENT_CLASS,
                                    getItemDetailFragmentClass().getCanonicalName());

                            onBeforeDetailedActivityStart(context, intent, item.getId(), position);
                            context.startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_none);
                        }
                    }
                }
        );
    }

    protected void setOnClickListener_ListChildren(View view, final String itemId, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {{
                            startViewChildrenActivity(itemId, position);
                        }
                    }
                }
        );
    };

    protected void onBeforeChilActivityStart(Context context, Intent intent, String itemId, int position) {
    }

    protected void onBeforeDetailedActivityStart(Context context, Intent intent, String itemId, int position) {
    }


    protected  abstract <T extends Activity> Class<T> getViewChildrenActivityClass();
    protected  abstract <T extends Activity> Class<T> getItemDetailActivityClass();
    protected  abstract <T extends Fragment> Class<T> getItemDetailFragmentClass();
    protected abstract int getItemDetailLayoutId();
}


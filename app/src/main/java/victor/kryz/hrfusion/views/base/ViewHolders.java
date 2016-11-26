package victor.kryz.hrfusion.views.base;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import victor.kryz.hrfusion.R;
import victor.kryz.hrfusion.hrdb.HrItem;

public class ViewHolders {

    protected static String LOG_TAG = ViewHolders.class.getSimpleName();

    /**
     * Creates "ViewHolder" by class object.
     *
     * @param context - context
     * @param holderClass - class of holder to create
     * @param view - view for holding
     * @param <T> - class of entity specific "ViewHolder";
     *
     * @return - instance of "ViewHolder"
     *           or raises exception in case cannot create required object
     */
    public static <T extends RecyclerView.ViewHolder> T createInstance(Context context, Class<T> holderClass, View view)
    {
        T holder = null;
        Exception currException = null;
        try {
            final Constructor<T> constr = holderClass.getConstructor(View.class);
            holder  = constr.newInstance(view);
        } catch (NoSuchMethodException e) {
            Log.e(LOG_TAG, e.getMessage());
            currException = e;
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, e.getMessage());
            currException = e;
        } catch (InstantiationException e) {
            Log.e(LOG_TAG, e.getMessage());
            currException = e;
        } catch (InvocationTargetException e) {
            Log.e(LOG_TAG, e.getMessage());
            currException = e;
        }

        if ( null != currException)
        {
            final String strMsg = context.getResources().
                    getString(R.string.cannot_create_vholder,
                            holderClass.getName(), currException.getLocalizedMessage());
           throw new RuntimeException(strMsg);
        }

        return holder;
    }

    /**
     * BaseViewHolder - base class for all "view holders"
     */
    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public HrItem mItem;
        public final View mView;
        public final TextView mTitleView;
        public final TextView mDescrView;
        public final ImageView mDropDownView;
        public final ImageView mDetailsView;

        public BaseViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView)view.findViewById(R.id.tvTitle);
            mDescrView = (TextView)view.findViewById(R.id.tvId);
            mDropDownView = (ImageView)view.findViewById(R.id.imgDropDown);
            mDetailsView = (ImageView)view.findViewById(R.id.iconDetails);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}

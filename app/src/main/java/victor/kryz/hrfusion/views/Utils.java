package victor.kryz.hrfusion.views;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.view.View;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils
{
     public static class TextView {
        View baseView;
        boolean bThowIfNotFound = false;

        public TextView(View baseView) {
            this.baseView = baseView;
        }

         public TextView(View baseView, boolean bThowIfNotFound) {
            this(baseView);
            this.bThowIfNotFound = bThowIfNotFound;
        }

         public void setText(int tvId, CharSequence text){
            android.widget.TextView tv = (android.widget.TextView)baseView.findViewById(tvId);
            if ( null != tv)
                tv.setText(text);
            else if ( bThowIfNotFound )
                throw new RuntimeException("TextView " + tvId + " not found");
        }
    }


    public static String salaryToString(Double salary, Locale locale)
    {
        String strSalary;
        if ( null == locale )
            strSalary = salary.toString();
        else
        {
            NumberFormat n = NumberFormat.getCurrencyInstance(locale);
            strSalary = n.format(salary);
        }

        return strSalary;
    };
};

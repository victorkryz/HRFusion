package victor.kryz.hrfusion.app;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.app.Application;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HRFusion extends Application
{
    private static HRFusion mApp;
    private static LocalesStock mLocaleStock;

    public static class LocalesStock {

        private static final Map<String, Locale> mLocales =
                                        new HashMap<String, Locale>();

        public Locale getLocale(String countryCode)
        {
            if ( mLocales.isEmpty() )
            {
                Locale[] ls = Locale.getAvailableLocales();
                for ( Locale locale : ls) {
                    mLocales.put(locale.getCountry(), locale);
                }
            }

            return mLocales.get(countryCode);
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mApp = this;
    }

    public static HRFusion getApplication() {
        return mApp;
    }

    public synchronized LocalesStock getLocalesStock()
    {
        if ( null == mLocaleStock )
            mLocaleStock = new LocalesStock();

        return mLocaleStock;
    }
}

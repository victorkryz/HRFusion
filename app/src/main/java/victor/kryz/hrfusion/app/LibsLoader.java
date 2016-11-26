package victor.kryz.hrfusion.app;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

public class LibsLoader {

    protected static String LOG_TAG = LibsLoader.class.getSimpleName();

    public static boolean DEBUG_MODE = true;

    public static void load()
    {

/*
        List of needed POCO libraries:
        -------------------------------------------------------------------------------------------
          "PocoFoundation(d)",
          "PocoUtil(d)", "PocoData(d)", "PocoXML(d)",
          "PocoDataSQLite(d)", "PocoJSON(d)"

         According to this: https://developer.android.com/ndk/guides/cpp-support.html
          ("You must load the libraries in reverse dependency order ..." ) or
         this: https://groups.google.com/forum/?fromgroups#!msg/android-ndk/J3lzK4X--bM/4YaijymZy_AJ
         we need to load all libraries,
         but actually it is working even only "HRFusion" library is loaded (at least on my tests).
        -------------------------------------------------------------------------------------------
*/

        try {
                System.loadLibrary("gnustl_shared");
                System.loadLibrary("log");
                System.loadLibrary(composeLibName("PocoFoundation"));
                System.loadLibrary(composeLibName("PocoXML"));
                System.loadLibrary(composeLibName("PocoJSON"));
                System.loadLibrary(composeLibName("PocoUtil"));
                System.loadLibrary(composeLibName("PocoData"));
                System.loadLibrary(composeLibName("PocoDataSQLite"));
                System.loadLibrary("HRFusion");
            }
            catch(Error e)
            {
                Log.e(LOG_TAG, e.getMessage());
                throw e;
            }
    }

    static String composeLibName(@NonNull String strInitialLibName)
    {
        String strResult = strInitialLibName;
        if ( DEBUG_MODE )
            strResult += "d";
        return strResult;
    }

}

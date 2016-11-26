package victor.kryz.hrfusion.app;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.content.res.AssetManager.ACCESS_STREAMING;

public class AssetsFile
{
    Context mContext;

    public AssetsFile(Context context) {
        mContext = context;
    }

    /**
     * Rewrites file from assets on the storage and returns it's full path.
     *
     * @param strFileName
     * @return file full path;
     * @throws IOException
     */
    public String update(String strFileName) throws IOException
    {
        String strFullPath = null;

        InputStream assStream = null;
        FileOutputStream fileStream = null;

        try
        {
            try
            {
                AssetManager am = mContext.getAssets();
                assStream = am.open(strFileName, ACCESS_STREAMING);

                fileStream = mContext.openFileOutput(strFileName, Context.MODE_PRIVATE);
                updateFileFromStream(assStream, fileStream);

                File fstg = mContext.getFileStreamPath(strFileName);
                strFullPath = fstg.getAbsolutePath();
            }
            finally
            {
                if ( fileStream != null )
                    fileStream.close();

                if ( assStream != null )
                    assStream.close();
            }

        } catch (IOException e) {
            throw e;
        }

        return strFullPath;
    }


    private static void updateFileFromStream(InputStream srcStream, FileOutputStream targStream) throws IOException
    {
        final int c_items = 1024;
        byte chBuff[] = new byte[c_items+16];
        int sz;
        while ((sz = srcStream.read(chBuff, 0, c_items)) != -1 )
            targStream.write(chBuff, 0, sz);
    }
}

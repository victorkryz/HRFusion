package victor.kryz.hrfusion.hrdb.sqlt;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import android.content.Context;
import java.io.IOException;
import victor.kryz.hrfusion.hrdb.Session;
import victor.kryz.hrfusion.hrdb.SessionCloseNotifier;
import victor.kryz.hrfusion.jni.PocoException;

/**
 * Simple single-session's session factory
 * (it's able to factorize only single session instance)
 */
public class SessionFactory {

    public static final String HR_DEFAULT_DB_FILE_NAME = "hr.db";

    static SessionImpl session;

    /**
     * Creates session based on default db-name;
     *
     * @param context;
     * @return session
     * @throws IOException
     */
    public static synchronized Session createSession(Context context) throws IOException, PocoException
    {
        victor.kryz.hrfusion.app.AssetsFile dbFile =
                new victor.kryz.hrfusion.app.AssetsFile(context);
        final String strFilePath = dbFile.update(HR_DEFAULT_DB_FILE_NAME);
        return SessionFactory.createSession(strFilePath);
    }

    /**
     * @param strDbFile - SQLite database file
     * @return Session object
     */
    public static synchronized Session createSession(final String strDbFile) throws PocoException {
        if (null == session) {
            session = SessionImpl.createInstance(strDbFile,
                    new SessionCloseNotifier() {
                        @Override
                        public void onSessionClose(Session session) {
                            SessionFactory.session = null;
                        }
                    }
            );
        }
        return session;
    }

    public static Session getSession() {
        assert session != null;
        return session;
    }
}

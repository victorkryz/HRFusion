package victor.kryz.hrfusion.entities;

/**
 * HRFusion (unit tests)
 *
 * @author Victor Kryzhanivskyi
 */

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import victor.kryz.hrfusion.app.LibsLoader;
import victor.kryz.hrfusion.hrdb.Session;
import victor.kryz.hrfusion.hrdb.sqlt.SessionFactory;


@RunWith(Suite.class)
@Suite.SuiteClasses({ReadRegionsTest.class, ReadLocationsTest.class, ReadCountriesTest.class,
                        ReadDepartmentsTest.class,  ReadEmployeesTest.class, ReadJobHistoryTest.class})
public class ReadHREntitiesFixture
{
    public static Session mSession;
    static final String TRACE_TAG = ReadHREntitiesFixture.class.getSimpleName();

    @ClassRule
    public static ExternalResource getResource(){
        return new ExternalResource() {
            @Override
            protected void before() throws Throwable {
                init();
            };

            @Override
            protected void after() {
                deinitSession();
            };
        };
    }

    public static void init() throws Throwable {
        initLibs();
        initSession();
    }

    private static void initLibs(){
            LibsLoader.load();
    }

    private static void initSession() throws Throwable {
        Context appContext = InstrumentationRegistry.getTargetContext();
        mSession = SessionFactory.createSession(appContext);
    }

    private static void deinitSession()  {
        if ( null != mSession ) {
            mSession.close();
            mSession = null;
        }
    }
}

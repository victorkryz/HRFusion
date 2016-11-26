package victor.kryz.hrfusion.hrdb.sqlt;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import victor.kryz.hrfusion.hrdb.Department;
import victor.kryz.hrfusion.hrdb.Employee;
import victor.kryz.hrfusion.hrdb.HrItem;
import victor.kryz.hrfusion.hrdb.JobStage;
import victor.kryz.hrfusion.hrdb.Location;
import victor.kryz.hrfusion.hrdb.Session;
import victor.kryz.hrfusion.hrdb.SessionCloseNotifier;
import victor.kryz.hrfusion.jni.PocoException;
import victor.kryz.hrfusion.jni.ReflectionException;

class SessionImpl implements Session
{
    public static SessionImpl createInstance(String strDbFile, SessionCloseNotifier closeNotifier) throws PocoException
    {
            checkDbFile(strDbFile);
            return new SessionImpl(strDbFile, closeNotifier);
    }

    private String strDbFile;
    SessionCloseNotifier closeNotifier;

    private SessionImpl(String strDbFile, SessionCloseNotifier closeNotifier) {
        this.strDbFile = strDbFile;
        this.closeNotifier = closeNotifier;
    }

    public void close() {
        if ( null != closeNotifier)
            closeNotifier.onSessionClose(this);
    }

    @Override
    public List<HrItem> getRegions () throws PocoException
    {
        HrItem[] items = getRegionsNt();
        return new ArrayList(Arrays.asList(items));
    }

    @Override
    public List<HrItem> getCountries(String regionId) throws PocoException {
        HrItem[] items = getCountriesNt(regionId);
        return new ArrayList(Arrays.asList(items));
    }

    @Override
    public List<Location> getLocations(String countryId) throws PocoException {
        Location[] items = getLocationsNt(countryId);
        return new ArrayList(Arrays.asList(items));
    }

    @Override
    public List<Department> getDepartments(String locationId) throws PocoException {
        Department[] items = getDepartmentsNt(locationId);
        return new ArrayList(Arrays.asList(items));
    }

    @Override
    public List<Employee> getEmployees(String departmentId) throws PocoException {
        Employee[] items = getEmployeesNt(departmentId);
        return new ArrayList(Arrays.asList(items));
    }

    @Override
    public List<JobStage> getJobHistory(String emloyeeId) throws PocoException {
        JobStage[] items = getJobHistoryNt(emloyeeId);
        return new ArrayList(Arrays.asList(items));
    }

    //& native interface:
    protected static native void checkDbFile(String strDbFile) throws PocoException, ReflectionException;
    protected native HrItem[] getRegionsNt() throws PocoException, ReflectionException;;
    protected native HrItem[] getCountriesNt(String regionId) throws PocoException, ReflectionException;
    protected native Location[] getLocationsNt(String countryId) throws PocoException, ReflectionException;
    protected native Department[] getDepartmentsNt(String locationId) throws PocoException, ReflectionException;
    protected native Employee[] getEmployeesNt(String departmentId) throws PocoException, ReflectionException;
    protected native JobStage[] getJobHistoryNt(String emloyeeId) throws PocoException, ReflectionException;
}



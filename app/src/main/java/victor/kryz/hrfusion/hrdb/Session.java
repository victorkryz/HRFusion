package victor.kryz.hrfusion.hrdb;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import java.util.List;
import victor.kryz.hrfusion.jni.PocoException;


public interface Session {
     List<HrItem> getRegions() throws PocoException;
     List<HrItem> getCountries(String regionId) throws PocoException;
     List<Location> getLocations(String countryId) throws PocoException;
     List<Department> getDepartments(String locationId) throws PocoException;
     List<Employee> getEmployees(String departmentId) throws PocoException;
     List<JobStage> getJobHistory(String emloyeeId) throws PocoException;

     void close();
}

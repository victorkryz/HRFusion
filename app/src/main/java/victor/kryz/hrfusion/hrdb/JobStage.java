package victor.kryz.hrfusion.hrdb;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

public class JobStage extends JobInfo {
    String  departmentId, departmentName,
            startDate, endDate;

    public JobStage(String id, String name) {
        super(id, name);
    }

    @Override
    public String getTitle() {
        return name;
    }
    public String getDepartmentName() {
        return departmentName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}

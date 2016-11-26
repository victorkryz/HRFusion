package victor.kryz.hrfusion.hrdb;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

public class JobInfo extends HrItem {
    double minSalary, maxSalary;

    public JobInfo(String id, String name) {
        super(id, name);
    }

    public double getMinSalary() {
        return minSalary;
    }
    public double getMaxSalary() {
        return maxSalary;
    }
}

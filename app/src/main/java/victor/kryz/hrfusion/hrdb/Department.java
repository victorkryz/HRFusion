package victor.kryz.hrfusion.hrdb;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

public class Department extends HrItem {

    protected String managerId;

    public Department(String id, String name) {
        super(id, name);
    }

    public String getManagerId() {
        return managerId;
    }
}

package victor.kryz.hrfusion.hrdb;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

public class Employee extends HrItem {
    String  lastName,  phoneNumber,
            email, hireDate, jobId, jobTitle;
    float comissionPct;
    double salary;
    boolean isMngr = false;

    public Employee(String id, String name) {
        super(id, name);
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getHireDate() {
        return hireDate;
    }

    public String getJobId() {
        return jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public float getComissionPct() {
        return comissionPct;
    }

    public double getSalary() {
        return salary;
    }

    public boolean isMngr() {
        return isMngr;
    }

    @Override
    public String getTitle(){
        return name + " " + lastName;
    }
};



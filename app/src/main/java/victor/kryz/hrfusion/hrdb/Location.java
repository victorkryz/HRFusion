package victor.kryz.hrfusion.hrdb;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

public class Location extends HrItem {

    protected String streetAddress;
    protected String postalCode;
    protected String city;
    protected String stateProvince;
    protected String countryId;

    public Location() {
    }

    public Location(String id) {
        super(id);
    }

    public String getCountryId() {
        return countryId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    @Override
    public String getTitle() {
        return city;
    }
}

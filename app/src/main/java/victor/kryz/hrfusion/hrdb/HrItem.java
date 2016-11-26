package victor.kryz.hrfusion.hrdb;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

import java.io.Serializable;

public class HrItem  implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String id;
    protected String name;

    public HrItem(){
    }
    public HrItem(String id) {
        this.id = id;
    }

    public HrItem(String id, String name) {
        this(id);
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return name;
    }
}

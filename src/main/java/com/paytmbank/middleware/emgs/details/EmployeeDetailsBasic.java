package com.paytmbank.middleware.emgs.details;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeDetailsBasic {
    private String status;
    private String errorDesc;
    private String fname, lname, eid;

    public EmployeeDetailsBasic() {
        errorDesc = "No Error.";
        status = "Success!";
        fname = null;
        lname = null;
        eid = null;
    }

    public EmployeeDetailsBasic(String eid, String fname, String lname) {
        errorDesc = "No Error.";
        status = "Success!";
        this.eid = eid;
        this.fname = fname;
        this.lname = lname;
    }
}

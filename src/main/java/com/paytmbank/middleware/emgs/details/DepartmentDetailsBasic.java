package com.paytmbank.middleware.emgs.details;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class DepartmentDetailsBasic {
    private String status;
    private String errorDesc;
    private String name, deptId;

    public DepartmentDetailsBasic() {
        errorDesc = "No Error.";
        status = "Success!";
        name = null;
        deptId = null;
    }

    public DepartmentDetailsBasic(String deptId, String name) {
        errorDesc = "No Error.";
        status = "Success!";
        this.deptId = deptId;
        this.name = name;
    }
}
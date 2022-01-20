package com.paytmbank.middleware.emgs.details;

import com.paytmbank.middleware.emgs.entity.Project;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ProjectDetailsBasic {
    private Project prj;
    private String errorDesc;
    private String status;

    public ProjectDetailsBasic() {
        errorDesc = "No error.";
        status = "Success!";
        prj = null;
    }
}

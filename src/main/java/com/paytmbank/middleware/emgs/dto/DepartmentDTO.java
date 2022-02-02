package com.paytmbank.middleware.emgs.dto;

import com.paytmbank.middleware.emgs.entity.Department;
import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.entity.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDTO {
    private String deptID;
    private String name;

    private String desc; // description of the depratment

    private List<Employee> emps;

    private List<Project> projects;

    public Department getDepartment() {
        return new Department(this);
    }
}

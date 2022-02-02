package com.paytmbank.middleware.emgs.dto;

import com.paytmbank.middleware.emgs.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDTO {
    private String eid;

    private String email;

    private String phone;
    private String password;
    private String role;

    private String fname;
    private String sname;
    private Timestamp createdDate;
    private Timestamp deletedDate;
    private boolean isActive;

    private Department dpt;

    private Project pid;

    private Leave lid;

    private Role rid;

    private List<Ticket> tickets;

    public Employee getEmployee() {
        return new Employee(this);
    }
}

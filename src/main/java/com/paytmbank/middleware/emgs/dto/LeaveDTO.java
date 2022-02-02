package com.paytmbank.middleware.emgs.dto;

import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.entity.Leave;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToOne;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class LeaveDTO {
    private long lid;
    private String reason;
    private Date startDate;
    private Date endDate;

    private boolean isPaid;

    private Employee emp;

    public Leave getLeave() {
        return new Leave(this);
    }
}

package com.paytmbank.middleware.emgs.dto;

import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.entity.Ticket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class TicketDTO {
    private Long id;

    private String title;
    private String description;
    private Timestamp createdDate;
    private Timestamp resolvedDate;

    private Employee raisedBy;

    public Ticket getTicket() {
        return new Ticket(this);
    }
}

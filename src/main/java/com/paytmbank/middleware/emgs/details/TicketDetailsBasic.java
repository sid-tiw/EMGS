package com.paytmbank.middleware.emgs.details;

import com.paytmbank.middleware.emgs.entity.Ticket;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TicketDetailsBasic {
    private Ticket tc;
    private String status, errorDesc;

    public TicketDetailsBasic(Ticket tc) {
        this.tc = tc;
        status = "Failure!";
        errorDesc = "No error.";
    }

    public TicketDetailsBasic() {
        status = "Failure!";
        errorDesc = "No error.";
    }
}

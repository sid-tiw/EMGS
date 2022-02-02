package com.paytmbank.middleware.emgs.entity;

import com.paytmbank.middleware.emgs.dto.TicketDTO;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter @Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private Timestamp createdDate;
    private Timestamp resolvedDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "raisedBy")
    private Employee raisedBy;

    public Ticket(TicketDTO ticketDTO) {
        id = ticketDTO.getId();
        title = ticketDTO.getTitle();
        description = ticketDTO.getDescription();
        createdDate = ticketDTO.getCreatedDate();
        resolvedDate = ticketDTO.getResolvedDate();
        raisedBy = ticketDTO.getRaisedBy();
    }
}

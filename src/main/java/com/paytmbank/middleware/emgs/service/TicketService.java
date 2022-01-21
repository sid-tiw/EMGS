package com.paytmbank.middleware.emgs.service;

import com.paytmbank.middleware.emgs.Repository.TicketRepository;
import com.paytmbank.middleware.emgs.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class TicketService {
    @Autowired
    TicketRepository ticketRepo;

    public void saveTicket(Ticket ticket) {
        /* Add the date on which the ticket is raised. */
        ticket.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        ticketRepo.save(ticket);
    }

    public List<Ticket> listAll(int pgNo) {
        return ticketRepo.findAll(PageRequest.of(pgNo, 5)).toList();
    }
}

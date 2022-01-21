package com.paytmbank.middleware.emgs.Repository;

import com.paytmbank.middleware.emgs.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}

package com.devsato.tickets.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsato.tickets.domain.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
  int countByTicketTypeId(UUID ticketTypeId);
}

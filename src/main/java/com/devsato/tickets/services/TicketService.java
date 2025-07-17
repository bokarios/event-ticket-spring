package com.devsato.tickets.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.devsato.tickets.domain.entities.Ticket;

public interface TicketService {
  Page<Ticket> listTicketsForUser(UUID userId, Pageable pageable);

  Optional<Ticket> getUserTicket(UUID userId, UUID ticketId);
}

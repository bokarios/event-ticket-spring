package com.devsato.tickets.services;

import java.util.UUID;

import com.devsato.tickets.domain.entities.Ticket;

public interface TicketTypeService {
  Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}

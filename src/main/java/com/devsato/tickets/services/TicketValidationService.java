package com.devsato.tickets.services;

import java.util.UUID;

import com.devsato.tickets.domain.entities.TicketValidation;

public interface TicketValidationService {
  TicketValidation validateTicketByQrCode(UUID qrCodeId);

  TicketValidation validateTicketManually(UUID ticketId);
}

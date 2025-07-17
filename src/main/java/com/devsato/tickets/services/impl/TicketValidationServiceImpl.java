package com.devsato.tickets.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.devsato.tickets.domain.entities.QrCode;
import com.devsato.tickets.domain.entities.QrCodeStatusEnum;
import com.devsato.tickets.domain.entities.Ticket;
import com.devsato.tickets.domain.entities.TicketValidation;
import com.devsato.tickets.domain.entities.TicketValidationMethod;
import com.devsato.tickets.domain.entities.TicketValidationStatusEnum;
import com.devsato.tickets.exceptions.QrCodeNotFoundException;
import com.devsato.tickets.exceptions.TicketNotFoundException;
import com.devsato.tickets.repositories.QrCodeRepository;
import com.devsato.tickets.repositories.TicketRepository;
import com.devsato.tickets.repositories.TicketValidationRepository;
import com.devsato.tickets.services.TicketValidationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {
  private final QrCodeRepository qrCodeRepository;
  private final TicketValidationRepository ticketValidationRepository;
  private final TicketRepository ticketRepository;

  @Override
  public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
    QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
        .orElseThrow(() -> new QrCodeNotFoundException(
            String.format("Qr Code with ID %s was not found", qrCodeId)));

    Ticket ticket = qrCode.getTicket();

    return validateTicket(ticket, TicketValidationMethod.QR_SCAN);
  }

  @Override
  public TicketValidation validateTicketManually(UUID ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(TicketNotFoundException::new);
    return validateTicket(ticket, TicketValidationMethod.MANUAL);
  }

  private TicketValidation validateTicket(Ticket ticket, TicketValidationMethod method) {
    TicketValidation ticketValidation = new TicketValidation();
    ticketValidation.setTicket(ticket);
    ticketValidation.setValidationMethod(method);

    TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations()
        .stream()
        .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
        .findFirst()
        .map(v -> TicketValidationStatusEnum.INVALID)
        .orElse(TicketValidationStatusEnum.VALID);

    ticketValidation.setStatus(ticketValidationStatus);

    return ticketValidationRepository.save(ticketValidation);
  }

}

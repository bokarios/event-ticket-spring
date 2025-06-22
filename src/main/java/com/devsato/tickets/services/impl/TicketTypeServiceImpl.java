package com.devsato.tickets.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.devsato.tickets.domain.entities.Ticket;
import com.devsato.tickets.domain.entities.TicketStatusEnum;
import com.devsato.tickets.domain.entities.TicketType;
import com.devsato.tickets.domain.entities.User;
import com.devsato.tickets.exceptions.TicketTypeNotFoundException;
import com.devsato.tickets.exceptions.TicketsSoldOutException;
import com.devsato.tickets.exceptions.UserNotFoundException;
import com.devsato.tickets.repositories.TicketRepository;
import com.devsato.tickets.repositories.TicketTypeRepository;
import com.devsato.tickets.repositories.UserRepository;
import com.devsato.tickets.services.QrCodeService;
import com.devsato.tickets.services.TicketTypeService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

  private final UserRepository userRepository;
  private final TicketTypeRepository ticketTypeRepository;
  private final TicketRepository ticketRepository;
  private final QrCodeService qrCodeService;

  @Override
  @Transactional
  public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(
            String.format("User with ID: %s was not found", userId)));

    TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
        .orElseThrow(() -> new TicketTypeNotFoundException(
            String.format("Ticket type with ID %s was not found", ticketTypeId)));

    int purchasedTickets = ticketRepository.countByTicketTypeId(ticketTypeId);
    Integer totalAvailable = ticketType.getTotalAvailable();

    if (purchasedTickets + 1 > totalAvailable) {
      throw new TicketsSoldOutException();
    }

    Ticket ticket = new Ticket();
    ticket.setStatus(TicketStatusEnum.PURCHASED);
    ticket.setTicketType(ticketType);
    ticket.setPurchaser(user);

    Ticket savedTicket = ticketRepository.save(ticket);
    qrCodeService.generateQrCode(savedTicket);

    return ticketRepository.save(savedTicket);
  }

}

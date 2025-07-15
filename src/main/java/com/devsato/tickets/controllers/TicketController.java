package com.devsato.tickets.controllers;

import static com.devsato.tickets.utils.JwtUtil.parseUserId;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsato.tickets.domain.dtos.ListTicketResponseDto;
import com.devsato.tickets.mappers.TicketMapper;
import com.devsato.tickets.services.TicketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/tickets")
public class TicketController {

  private final TicketService ticketService;
  private final TicketMapper ticketMapper;

  @GetMapping
  public Page<ListTicketResponseDto> listTickets(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
    UUID userId = parseUserId(jwt);
    return ticketService.listTicketsForUser(userId, pageable).map(ticketMapper::toListTicketResponseDto);
  }
}

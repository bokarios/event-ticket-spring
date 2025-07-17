package com.devsato.tickets.controllers;

import static com.devsato.tickets.utils.JwtUtil.parseUserId;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsato.tickets.domain.dtos.GetTicketResponseDto;
import com.devsato.tickets.domain.dtos.ListTicketResponseDto;
import com.devsato.tickets.mappers.TicketMapper;
import com.devsato.tickets.services.QrCodeService;
import com.devsato.tickets.services.TicketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/tickets")
public class TicketController {

  private final TicketService ticketService;
  private final TicketMapper ticketMapper;
  private final QrCodeService qrCodeService;

  @GetMapping
  public Page<ListTicketResponseDto> listTickets(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
    UUID userId = parseUserId(jwt);
    return ticketService.listTicketsForUser(userId, pageable).map(ticketMapper::toListTicketResponseDto);
  }

  @GetMapping(path = "/{ticketId}")
  public ResponseEntity<GetTicketResponseDto> getTicket(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID ticketId) {
    UUID userId = parseUserId(jwt);
    return ticketService.getUserTicket(userId, ticketId)
        .map(ticketMapper::toGetTicketResponseDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(path = "/{ticketId}/qr-codes")
  public ResponseEntity<byte[]> getTicketQrCode(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID ticketId) {
    UUID userId = parseUserId(jwt);
    byte[] qrCodeImage = qrCodeService.getQrCodeForUserAndTicket(userId, ticketId);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);
    headers.setContentLength(qrCodeImage.length);

    return ResponseEntity.ok()
        .headers(headers)
        .body(qrCodeImage);
  }
}

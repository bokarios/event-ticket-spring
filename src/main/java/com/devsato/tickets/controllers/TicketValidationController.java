package com.devsato.tickets.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsato.tickets.domain.dtos.TicketValidationRequestDto;
import com.devsato.tickets.domain.dtos.TicketValidationResponseDto;
import com.devsato.tickets.domain.entities.TicketValidation;
import com.devsato.tickets.domain.entities.TicketValidationMethod;
import com.devsato.tickets.mappers.TicketValidationMapper;
import com.devsato.tickets.services.TicketValidationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/ticket-validations")
public class TicketValidationController {
  private final TicketValidationService ticketValidationService;
  private final TicketValidationMapper ticketValidationMapper;

  @PostMapping
  public ResponseEntity<TicketValidationResponseDto> validateTicket(
      @RequestBody TicketValidationRequestDto request) {
    TicketValidationMethod method = request.getMethod();

    TicketValidation ticketValidation;
    if (method.equals(TicketValidationMethod.MANUAL)) {
      ticketValidation = ticketValidationService.validateTicketManually(request.getId());
    } else {
      ticketValidation = ticketValidationService.validateTicketByQrCode(request.getId());
    }

    return ResponseEntity.ok(ticketValidationMapper.toTicketValidationResponseDto(ticketValidation));
  }

}

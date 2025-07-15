package com.devsato.tickets.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.devsato.tickets.domain.dtos.ListTicketResponseDto;
import com.devsato.tickets.domain.dtos.ListTicketTicketTypeResponseDto;
import com.devsato.tickets.domain.entities.Ticket;
import com.devsato.tickets.domain.entities.TicketType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
  ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);

  ListTicketResponseDto toListTicketResponseDto(Ticket ticket);
}

package com.devsato.tickets.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.devsato.tickets.domain.dtos.GetTicketResponseDto;
import com.devsato.tickets.domain.dtos.ListTicketResponseDto;
import com.devsato.tickets.domain.dtos.ListTicketTicketTypeResponseDto;
import com.devsato.tickets.domain.entities.Ticket;
import com.devsato.tickets.domain.entities.TicketType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
  ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);

  ListTicketResponseDto toListTicketResponseDto(Ticket ticket);

  @Mapping(target = "price", source = "ticket.ticketType.price")
  @Mapping(target = "description", source = "ticket.ticketType.description")
  @Mapping(target = "eventName", source = "ticket.ticketType.event.name")
  @Mapping(target = "eventVenue", source = "ticket.ticketType.event.venue")
  @Mapping(target = "eventStart", source = "ticket.ticketType.event.start")
  @Mapping(target = "eventEnd", source = "ticket.ticketType.event.end")
  GetTicketResponseDto toGetTicketResponseDto(Ticket ticket);
}

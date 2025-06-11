package com.devsato.tickets.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.devsato.tickets.domain.CreateEventRequest;
import com.devsato.tickets.domain.CreateTicketTypeRequest;
import com.devsato.tickets.domain.dtos.CreateEventRequestDto;
import com.devsato.tickets.domain.dtos.CreateEventResponseDto;
import com.devsato.tickets.domain.dtos.CreateTicketTypeRequestDto;
import com.devsato.tickets.domain.dtos.ListEventResponseDto;
import com.devsato.tickets.domain.dtos.ListEventTicketTypeResponseDto;
import com.devsato.tickets.domain.entities.Event;
import com.devsato.tickets.domain.entities.TicketType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
  CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

  CreateEventRequest fromDto(CreateEventRequestDto dto);

  CreateEventResponseDto toDto(Event event);

  ListEventTicketTypeResponseDto toDto(TicketType ticketType);

  ListEventResponseDto toListEventResponseDto(Event event);
}

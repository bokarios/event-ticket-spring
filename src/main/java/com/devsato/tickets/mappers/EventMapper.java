package com.devsato.tickets.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.devsato.tickets.domain.CreateEventRequest;
import com.devsato.tickets.domain.CreateTicketTypeRequest;
import com.devsato.tickets.domain.UpdateEventRequest;
import com.devsato.tickets.domain.UpdateTicketTypeRequest;
import com.devsato.tickets.domain.dtos.CreateEventRequestDto;
import com.devsato.tickets.domain.dtos.CreateEventResponseDto;
import com.devsato.tickets.domain.dtos.CreateTicketTypeRequestDto;
import com.devsato.tickets.domain.dtos.GetEventDetailsResponseDto;
import com.devsato.tickets.domain.dtos.GetEventTicketTypesResponseDto;
import com.devsato.tickets.domain.dtos.GetPublishedEventResponseDto;
import com.devsato.tickets.domain.dtos.GetPublishedEventTicketTypesResponseDto;
import com.devsato.tickets.domain.dtos.ListEventResponseDto;
import com.devsato.tickets.domain.dtos.ListEventTicketTypeResponseDto;
import com.devsato.tickets.domain.dtos.ListPublishedEventResponseDto;
import com.devsato.tickets.domain.dtos.UpdateEventRequestDto;
import com.devsato.tickets.domain.dtos.UpdateEventResponseDto;
import com.devsato.tickets.domain.dtos.UpdateTicketTypeRequestDto;
import com.devsato.tickets.domain.dtos.UpdateTicketTypeResponseDto;
import com.devsato.tickets.domain.entities.Event;
import com.devsato.tickets.domain.entities.TicketType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
  CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

  CreateEventRequest fromDto(CreateEventRequestDto dto);

  CreateEventResponseDto toDto(Event event);

  ListEventTicketTypeResponseDto toDto(TicketType ticketType);

  ListEventResponseDto toListEventResponseDto(Event event);

  GetEventTicketTypesResponseDto tGetEventTicketTypesResponseDto(TicketType ticketType);

  GetEventDetailsResponseDto tGetEventDetailsResponseDto(Event event);

  UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);

  UpdateEventRequest fromDto(UpdateEventRequestDto dto);

  UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);

  UpdateEventResponseDto toUpdateEventResponseDto(Event event);

  ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);

  GetPublishedEventTicketTypesResponseDto toGetPublishedEventTicketTypesResponseDto(TicketType ticketType);

  GetPublishedEventResponseDto tGetPublishedEventResponseDto(Event event);
}

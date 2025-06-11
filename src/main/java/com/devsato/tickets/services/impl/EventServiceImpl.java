package com.devsato.tickets.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsato.tickets.domain.CreateEventRequest;
import com.devsato.tickets.domain.entities.Event;
import com.devsato.tickets.domain.entities.TicketType;
import com.devsato.tickets.domain.entities.User;
import com.devsato.tickets.exceptions.UserNotFoundException;
import com.devsato.tickets.repositories.EventRepository;
import com.devsato.tickets.repositories.UserRepository;
import com.devsato.tickets.services.EventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final UserRepository userRepository;
  private final EventRepository eventRepository;

  @Override
  public Event createEvent(UUID organizerId, CreateEventRequest request) {
    User organizer = userRepository.findById(organizerId)
        .orElseThrow(() -> new UserNotFoundException(String.format("User with ID '%s' not found", organizerId)));

    Event event = new Event();

    List<TicketType> ticketTypesToCreate = request.getTicketTypes().stream().map(ticketType -> {
      TicketType ticketTypeToCreate = new TicketType();
      ticketTypeToCreate.setName(ticketType.getName());
      ticketTypeToCreate.setPrice(ticketType.getPrice());
      ticketTypeToCreate.setDescription(ticketType.getDescription());
      ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
      ticketTypeToCreate.setEvent(event);

      return ticketTypeToCreate;
    }).toList();

    event.setName(request.getName());
    event.setStart(request.getStart());
    event.setEnd(request.getEnd());
    event.setVenue(request.getVenue());
    event.setSalesStart(request.getSalesStart());
    event.setSalesEnd(request.getSalesEnd());
    event.setStatus(request.getStatus());
    event.setOrganizer(organizer);
    event.setTicketTypes(ticketTypesToCreate);

    return eventRepository.save(event);
  }

  @Override
  public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
    return eventRepository.findByOrganizerId(organizerId, pageable);
  }

}

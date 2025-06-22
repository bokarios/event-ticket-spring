package com.devsato.tickets.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsato.tickets.domain.CreateEventRequest;
import com.devsato.tickets.domain.UpdateEventRequest;
import com.devsato.tickets.domain.UpdateTicketTypeRequest;
import com.devsato.tickets.domain.entities.Event;
import com.devsato.tickets.domain.entities.EventStatusEnum;
import com.devsato.tickets.domain.entities.TicketType;
import com.devsato.tickets.domain.entities.User;
import com.devsato.tickets.exceptions.EventNotFoundException;
import com.devsato.tickets.exceptions.EventUpdateException;
import com.devsato.tickets.exceptions.TicketTypeNotFoundException;
import com.devsato.tickets.exceptions.UserNotFoundException;
import com.devsato.tickets.repositories.EventRepository;
import com.devsato.tickets.repositories.UserRepository;
import com.devsato.tickets.services.EventService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final UserRepository userRepository;
  private final EventRepository eventRepository;

  @Override
  @Transactional
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

  @Override
  public Optional<Event> getEventForOrganizer(UUID organizerId, UUID id) {
    return eventRepository.findByIdAndOrganizerId(id, organizerId);
  }

  @Override
  @Transactional
  public Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest request) {
    if (request.getId() == null) {
      throw new EventUpdateException("Event ID cannot be null");
    }

    if (!id.equals(request.getId())) {
      throw new EventUpdateException("Cannot update the ID of an event");
    }

    Event existingEvent = eventRepository.findByIdAndOrganizerId(id, organizerId)
        .orElseThrow(() -> new EventNotFoundException(
            String.format("Event with ID '%s' not exist", id)));

    existingEvent.setName(request.getName());
    existingEvent.setStart(request.getStart());
    existingEvent.setEnd(request.getEnd());
    existingEvent.setVenue(request.getVenue());
    existingEvent.setSalesStart(request.getSalesStart());
    existingEvent.setSalesEnd(request.getSalesEnd());
    existingEvent.setStatus(request.getStatus());

    Set<UUID> requestTicketTypeIds = request.getTicketTypes()
        .stream()
        .map(UpdateTicketTypeRequest::getId)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());

    existingEvent.getTicketTypes()
        .removeIf(existingTicketType -> !requestTicketTypeIds.contains(existingTicketType.getId()));

    Map<UUID, TicketType> existingTicketTypesIndex = existingEvent.getTicketTypes().stream()
        .collect(Collectors.toMap(TicketType::getId, Function.identity()));

    for (UpdateTicketTypeRequest ticketType : request.getTicketTypes()) {
      if (ticketType.getId() == null) {
        // Create
        TicketType ticketTypeToCreate = new TicketType();
        ticketTypeToCreate.setName(ticketType.getName());
        ticketTypeToCreate.setPrice(ticketType.getPrice());
        ticketTypeToCreate.setDescription(ticketType.getDescription());
        ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
        ticketTypeToCreate.setEvent(existingEvent);
        existingEvent.getTicketTypes().add(ticketTypeToCreate);
      } else if (existingTicketTypesIndex.containsKey(ticketType.getId())) {
        // Update
        TicketType existingTicketType = existingTicketTypesIndex.get(ticketType.getId());
        existingTicketType.setName(ticketType.getName());
        existingTicketType.setPrice(ticketType.getPrice());
        existingTicketType.setDescription(ticketType.getDescription());
        existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
      } else {
        throw new TicketTypeNotFoundException(
            String.format("Ticket type with ID '%s' does not exist", ticketType.getId()));
      }
    }

    return eventRepository.save(existingEvent);
  }

  @Override
  @Transactional
  public void deleteEventForOrganizer(UUID organizerId, UUID id) {
    getEventForOrganizer(organizerId, id).ifPresent(eventRepository::delete);
  }

  @Override
  public Page<Event> listPublishedEvents(Pageable pageable) {
    return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
  }

  @Override
  public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
    return eventRepository.searchEvents(query, pageable);
  }

  @Override
  public Optional<Event> publishedEvent(UUID id) {
    return eventRepository.findByIdAndStatus(id, EventStatusEnum.PUBLISHED);
  }

}

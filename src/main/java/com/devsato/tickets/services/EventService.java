package com.devsato.tickets.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.devsato.tickets.domain.CreateEventRequest;
import com.devsato.tickets.domain.UpdateEventRequest;
import com.devsato.tickets.domain.entities.Event;

public interface EventService {
  Event createEvent(UUID organizerId, CreateEventRequest request);

  Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);

  Optional<Event> getEventForOrganizer(UUID organizerId, UUID id);

  Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest request);

  void deleteEventForOrganizer(UUID organizerId, UUID id);

  Page<Event> listPublishedEvents(Pageable pageable);
}

package com.devsato.tickets.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.devsato.tickets.domain.CreateEventRequest;
import com.devsato.tickets.domain.entities.Event;

public interface EventService {
  Event createEvent(UUID organizerId, CreateEventRequest request);

  Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);
}

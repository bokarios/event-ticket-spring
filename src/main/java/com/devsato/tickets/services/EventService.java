package com.devsato.tickets.services;

import java.util.UUID;

import com.devsato.tickets.domain.CreateEventRequest;
import com.devsato.tickets.domain.entities.Event;

public interface EventService {
  Event createEvent(UUID organizerId, CreateEventRequest request);
}

package com.devsato.tickets.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsato.tickets.domain.CreateEventRequest;
import com.devsato.tickets.domain.UpdateEventRequest;
import com.devsato.tickets.domain.dtos.CreateEventRequestDto;
import com.devsato.tickets.domain.dtos.CreateEventResponseDto;
import com.devsato.tickets.domain.dtos.GetEventDetailsResponseDto;
import com.devsato.tickets.domain.dtos.ListEventResponseDto;
import com.devsato.tickets.domain.dtos.UpdateEventRequestDto;
import com.devsato.tickets.domain.dtos.UpdateEventResponseDto;
import com.devsato.tickets.domain.entities.Event;
import com.devsato.tickets.mappers.EventMapper;
import com.devsato.tickets.services.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/events")
public class EventController {

  private final EventService eventService;
  private final EventMapper eventMapper;

  @PostMapping
  public ResponseEntity<CreateEventResponseDto> createEvent(@RequestBody @Valid CreateEventRequestDto requestDto,
      @AuthenticationPrincipal Jwt jwt) {
    CreateEventRequest request = eventMapper.fromDto(requestDto);
    UUID userId = getUserId(jwt);
    Event createdEvent = eventService.createEvent(userId, request);
    CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);

    return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
  }

  @PutMapping(path = "/{eventId}")
  public ResponseEntity<UpdateEventResponseDto> updateEvent(@RequestBody @Valid UpdateEventRequestDto requestDto,
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable UUID eventId) {
    UpdateEventRequest request = eventMapper.fromDto(requestDto);
    UUID userId = getUserId(jwt);
    Event updatedEvent = eventService.updateEventForOrganizer(userId, eventId, request);
    UpdateEventResponseDto updateEventResponseDto = eventMapper.toUpdateEventResponseDto(updatedEvent);

    return new ResponseEntity<>(updateEventResponseDto, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Page<ListEventResponseDto>> listEvents(
      @AuthenticationPrincipal Jwt jwt,
      Pageable pageable) {
    UUID userId = getUserId(jwt);
    Page<Event> events = eventService.listEventsForOrganizer(userId, pageable);
    return ResponseEntity.ok(events.map(eventMapper::toListEventResponseDto));
  }

  @GetMapping(path = "/{eventId}")
  public ResponseEntity<GetEventDetailsResponseDto> getEvent(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable UUID eventId) {
    UUID userId = getUserId(jwt);
    return eventService.getEventForOrganizer(userId, eventId)
        .map(eventMapper::tGetEventDetailsResponseDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping(path = "/{eventId}")
  public ResponseEntity<Void> deleteEvent(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID eventId) {
    UUID userId = getUserId(jwt);
    eventService.deleteEventForOrganizer(userId, eventId);
    return ResponseEntity.noContent().build();
  }

  private UUID getUserId(Jwt jwt) {
    UUID userId = UUID.fromString(jwt.getSubject());
    return userId;
  }

}

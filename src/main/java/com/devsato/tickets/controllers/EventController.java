package com.devsato.tickets.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsato.tickets.domain.CreateEventRequest;
import com.devsato.tickets.domain.dtos.CreateEventRequestDto;
import com.devsato.tickets.domain.dtos.CreateEventResponseDto;
import com.devsato.tickets.domain.dtos.ListEventResponseDto;
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

  @GetMapping
  public ResponseEntity<Page<ListEventResponseDto>> listEvents(
      @AuthenticationPrincipal Jwt jwt,
      Pageable pageable) {
    UUID userId = getUserId(jwt);
    Page<Event> events = eventService.listEventsForOrganizer(userId, pageable);
    return ResponseEntity.ok(events.map(eventMapper::toListEventResponseDto));
  }

  private UUID getUserId(Jwt jwt) {
    UUID userId = UUID.fromString(jwt.getSubject());
    return userId;
  }

}

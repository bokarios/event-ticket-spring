package com.devsato.tickets.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsato.tickets.domain.dtos.GetPublishedEventResponseDto;
import com.devsato.tickets.domain.dtos.ListPublishedEventResponseDto;
import com.devsato.tickets.domain.entities.Event;
import com.devsato.tickets.mappers.EventMapper;
import com.devsato.tickets.services.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/published-events")
public class PublishedEventController {

  private final EventService eventService;
  private final EventMapper eventMapper;

  @GetMapping
  public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
      @RequestParam(required = false) String q, Pageable pageable) {
    Page<Event> events;

    if (q != null && !q.trim().isEmpty()) {
      events = eventService.searchPublishedEvents(q, pageable);
    } else {
      events = eventService.listPublishedEvents(pageable);
    }

    return ResponseEntity.ok(events.map(eventMapper::toListPublishedEventResponseDto));
  }

  @GetMapping(path = "/{eventId}")
  public ResponseEntity<GetPublishedEventResponseDto> getPublishedEvent(@PathVariable UUID eventId) {
    return eventService.publishedEvent(eventId)
        .map(eventMapper::tGetPublishedEventResponseDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

}

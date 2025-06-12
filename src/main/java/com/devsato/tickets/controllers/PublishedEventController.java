package com.devsato.tickets.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsato.tickets.domain.dtos.ListPublishedEventResponseDto;
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
  public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(Pageable pageable) {
    Page<ListPublishedEventResponseDto> publishedEvents = eventService.listPublishedEvents(pageable)
        .map(eventMapper::toListPublishedEventResponseDto);

    return ResponseEntity.ok(publishedEvents);
  }

}

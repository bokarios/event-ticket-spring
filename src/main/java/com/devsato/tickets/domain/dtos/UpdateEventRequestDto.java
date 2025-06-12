package com.devsato.tickets.domain.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.devsato.tickets.domain.entities.EventStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequestDto {
  @NotNull(message = "Event ID is required")
  private UUID id;

  @NotBlank(message = "Event name is required")
  private String name;

  private LocalDateTime start;
  private LocalDateTime end;

  @NotBlank(message = "Event venue information is required")
  private String venue;

  private LocalDateTime salesStart;
  private LocalDateTime salesEnd;

  @NotNull(message = "Event status must be provided")
  private EventStatusEnum status;

  @NotEmpty(message = "At least one ticket type is required")
  @Valid
  private List<UpdateTicketTypeRequestDto> ticketTypes;
}

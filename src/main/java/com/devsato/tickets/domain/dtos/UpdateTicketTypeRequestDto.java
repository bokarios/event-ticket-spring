package com.devsato.tickets.domain.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTicketTypeRequestDto {
  private UUID id;

  @NotBlank(message = "Ticket type name is required")
  private String name;

  @NotNull(message = "Ticket type price is required")
  @PositiveOrZero(message = "Price must be zero or greater")
  private Double price;

  private String description;
  private Integer totalAvailable;
}

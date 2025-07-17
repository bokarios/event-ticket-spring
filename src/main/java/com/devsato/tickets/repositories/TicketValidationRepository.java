package com.devsato.tickets.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsato.tickets.domain.entities.TicketValidation;

@Repository
public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {

}

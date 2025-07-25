package com.devsato.tickets.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsato.tickets.domain.entities.QrCode;
import com.devsato.tickets.domain.entities.QrCodeStatusEnum;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {
  Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID ticketPurchaserId);

  Optional<QrCode> findByIdAndStatus(UUID id, QrCodeStatusEnum status);
}

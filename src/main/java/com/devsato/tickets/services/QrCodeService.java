package com.devsato.tickets.services;

import com.devsato.tickets.domain.entities.QrCode;
import com.devsato.tickets.domain.entities.Ticket;

public interface QrCodeService {
  QrCode generateQrCode(Ticket ticket);
}

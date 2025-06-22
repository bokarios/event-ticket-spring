package com.devsato.tickets.exceptions;

public class QrCodeNotFoundException extends EventTicketException {
  public QrCodeNotFoundException() {
    super();
  }

  public QrCodeNotFoundException(String message) {
    super(message);
  }

  public QrCodeNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public QrCodeNotFoundException(Throwable cause) {
    super(cause);
  }

  protected QrCodeNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

package com.devsato.tickets.exceptions;

public class EventTicketException extends RuntimeException {
  public EventTicketException() {
    super();
  }

  public EventTicketException(String message) {
    super(message);
  }

  public EventTicketException(String message, Throwable cause) {
    super(message, cause);
  }

  public EventTicketException(Throwable cause) {
    super(cause);
  }

  protected EventTicketException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

package com.devsato.tickets.exceptions;

public class EventNotFoundException extends EventTicketException {
  public EventNotFoundException() {
    super();
  }

  public EventNotFoundException(String message) {
    super(message);
  }

  public EventNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public EventNotFoundException(Throwable cause) {
    super(cause);
  }

  protected EventNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

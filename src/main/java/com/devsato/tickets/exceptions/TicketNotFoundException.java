package com.devsato.tickets.exceptions;

public class TicketNotFoundException extends EventTicketException {
  public TicketNotFoundException() {
    super();
  }

  public TicketNotFoundException(String message) {
    super(message);
  }

  public TicketNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public TicketNotFoundException(Throwable cause) {
    super(cause);
  }

  protected TicketNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

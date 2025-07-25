package com.devsato.tickets.exceptions;

public class TicketTypeNotFoundException extends EventTicketException {
  public TicketTypeNotFoundException() {
    super();
  }

  public TicketTypeNotFoundException(String message) {
    super(message);
  }

  public TicketTypeNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public TicketTypeNotFoundException(Throwable cause) {
    super(cause);
  }

  protected TicketTypeNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

package com.jrfom.icelotto.model.websocket;

import org.threeten.bp.Instant;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class DrawingCreateMessage {
  private Instant date;

  public DrawingCreateMessage() {}

  public Instant getDate() {
    return date;
  }

  public void setDateWithInstant(Instant date) {
    this.date = date;
  }

  public void setDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd @ HH:mm Z");
    this.date = ZonedDateTime.parse(date, formatter).toInstant();
  }
}
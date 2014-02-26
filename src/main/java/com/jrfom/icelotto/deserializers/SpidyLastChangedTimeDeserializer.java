package com.jrfom.icelotto.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class SpidyLastChangedTimeDeserializer extends JsonDeserializer<Instant> {
  @Override
  public Instant deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String value = jp.getValueAsString().replaceAll(" UTC", "+00:00").replaceAll(" ", "T");
    OffsetDateTime dateTime = OffsetDateTime.parse(value);
    return Instant.from(dateTime);
  }
}
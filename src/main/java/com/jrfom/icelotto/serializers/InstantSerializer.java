package com.jrfom.icelotto.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.threeten.bp.Instant;

public class InstantSerializer extends JsonSerializer<Instant> {
  @Override
  public void serialize(Instant value, JsonGenerator jgen, SerializerProvider provider)
    throws IOException, JsonProcessingException
  {
    jgen.writeString(value.toString());
  }
}
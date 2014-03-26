package com.jrfom.icelotto.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jrfom.icelotto.model.MoneyDrawResult;


public class MoneyDrawResultSerializer extends JsonSerializer<MoneyDrawResult> {
  @Override
  public void serialize(MoneyDrawResult value, JsonGenerator jgen, SerializerProvider provider)
    throws IOException, JsonProcessingException
  {
    jgen.writeStartObject();
    jgen.writeObjectField("drawingId", value.getDrawing().getId());
    jgen.writeObjectField("poolId", value.getPrizePool().getId());
    jgen.writeObjectField("awarded", value.getAwarded().toString());
    jgen.writeObjectField("drawNumber", value.getDrawNumber());
    jgen.writeObjectField("amountWon", value.getAmountWon());
    jgen.writeObjectField("userId", value.getUser().getId());
    jgen.writeObjectField("userDisplayName", value.getUser().getDisplayName());
    jgen.writeEndObject();
  }
}
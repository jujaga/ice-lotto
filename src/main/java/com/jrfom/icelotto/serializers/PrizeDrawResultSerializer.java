package com.jrfom.icelotto.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jrfom.icelotto.model.PrizeDrawResult;

public class PrizeDrawResultSerializer extends JsonSerializer<PrizeDrawResult> {
  @Override
  public void serialize(PrizeDrawResult value, JsonGenerator jgen, SerializerProvider provider)
    throws IOException, JsonProcessingException
  {
    jgen.writeStartObject();

    jgen.writeObjectField("tierId", value.getPrizeTier().getId());
    jgen.writeObjectField("prizeId", value.getPrizeItem().getId());
    jgen.writeObjectField("userId", value.getUser().getId());
    jgen.writeObjectField("userDisplayName", value.getUser().getDisplayName());
    jgen.writeObjectField("drawNumber", value.getDrawNumber());
    jgen.writeObjectField("awarded", value.getAwarded().toString());

    jgen.writeEndObject();
  }
}
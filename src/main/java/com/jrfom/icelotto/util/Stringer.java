package com.jrfom.icelotto.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stringer {
  private static final Logger log = LoggerFactory.getLogger(Stringer.class);

  public static <T> String jsonString(T obj) {
    return Stringer.jsonString(obj, false);
  }

  public static <T> String jsonString(T obj, boolean pretty) {
    ObjectMapper mapper = new ObjectMapper();
    String json = "<could not serialize object>";

    try {
      if (pretty) {
        json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
      } else {
        json = mapper.writeValueAsString(obj);
      }
    } catch (JsonProcessingException e) {
      log.error("Could not create JSON string: `{}`", e.getMessage());
      log.debug(e.toString());
    }

    return json;
  }
}
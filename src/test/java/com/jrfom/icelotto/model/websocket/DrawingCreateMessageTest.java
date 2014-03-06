package com.jrfom.icelotto.model.websocket;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

import static org.junit.Assert.assertTrue;

public class DrawingCreateMessageTest {
  private static final Logger log = LoggerFactory.getLogger(DrawingCreateMessageTest.class);

  @Test
  public void test() {
    this.log.info("Running DrawingCreateMessage test");
    DrawingCreateMessage message = new DrawingCreateMessage();
    Instant testDate = OffsetDateTime.of(2014, 3, 5, 16, 30, 0, 0, ZoneOffset.ofHours(-8)).toInstant();
    String strDate = "2014-03-05 @ 16:30 -0800";

    message.setDate(strDate);
    assertTrue(message.getDate().equals(testDate));
  }
}
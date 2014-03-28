package com.jrfom.icelotto.security;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalPasswordEncoderTest {
  private static final Logger log = LoggerFactory.getLogger(LocalPasswordEncoderTest.class);

  @Test
  public void test() {
    log.info("Running LocalPasswordEncoder test");
    LocalPasswordEncoder encoder = new LocalPasswordEncoder();
    String encoded = encoder.encode("password");

    log.info("Encoded password = `{}`", encoded);
  }
}
package com.jrfom.icelotto.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LocalPasswordEncoder implements PasswordEncoder {
  private static final Logger log = LoggerFactory.getLogger(LocalPasswordEncoder.class);

  private BCryptPasswordEncoder encoder;
  private final int STRENGTH = 13;

  public LocalPasswordEncoder() {
    log.debug("Creating local password encoder");
    SecureRandom random = null;

    try {
      random = SecureRandom.getInstance("SHA1PRNG");
    } catch (NoSuchAlgorithmException e) {
      log.error("Could not find random algorithm: `{}`", e.getMessage());
      log.debug(e.toString());
    }

    if (random != null) {
      log.debug("Creating with custom random");
      this.encoder = new BCryptPasswordEncoder(this.STRENGTH, random);
    } else {
      log.debug("Creating with default random");
      this.encoder = new BCryptPasswordEncoder(this.STRENGTH);
    }
  }

  @Override
  public String encode(CharSequence rawPassword) {
    log.debug("Encoding password: `{}`", rawPassword);
    String result = this.encoder.encode(rawPassword);
    log.debug("Encoded password: `{}`", result);
    return result;
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    log.debug("Matching password: [`{}`, `{}`]", rawPassword, encodedPassword);
    return this.encoder.matches(rawPassword, encodedPassword);
  }
}
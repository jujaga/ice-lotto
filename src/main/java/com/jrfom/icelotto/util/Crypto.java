package com.jrfom.icelotto.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crypto {
  private static final Logger log = LoggerFactory.getLogger(Crypto.class);

  /**
   * Get a random integer between 1 and the {@code maxNumber}.
   *
   * @param maxNumber The upper bound of the random range.
   *
   * @return An integer between 1 and {@code maxNumber} (both inclusive).
   */
  public static int randomInt(int maxNumber) {
    log.debug("Getting random integer with maximum = {}", maxNumber);
    int result = 0;

    try {
      SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
      byte[] seed = new byte[4098];
      random.nextBytes(seed);
      random.setSeed(seed);
      result = random.nextInt(maxNumber) + 1; // 1 - 10 instead of 0 - 9
    } catch (NoSuchAlgorithmException e) {
      log.error("Could not find secure random algorithm: `{}`", e.getMessage());
      log.debug(e.toString());
    }

    return result;
  }
}
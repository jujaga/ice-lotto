package com.jrfom.icelotto.exception;

public class PrizeTierNotFoundException extends RuntimeException {
  public PrizeTierNotFoundException() {
    super("Could not find prize tier in database");
  }
}
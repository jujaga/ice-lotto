package com.jrfom.icelotto.exception;

public class GameItemNotFoundException extends RuntimeException {
  public GameItemNotFoundException() {
    super("Could not find game item in database");
  }

  public GameItemNotFoundException(String message) {
    super(message);
  }
}
package com.abc.nz.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RateCalculateException extends RuntimeException{

  public RateCalculateException(final String message) {
    super("RateCalculateException error occurred: " + message);
    log.error("RateCalculateException error occurred: {}", message);
  }
}

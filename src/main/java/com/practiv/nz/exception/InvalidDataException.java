package com.practiv.nz.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidDataException extends RuntimeException{

  public InvalidDataException(final String message) {
    super("InvalidDataException error occurred: " + message);
    log.error("InvalidDataException error occurred: {}", message);
  }
}

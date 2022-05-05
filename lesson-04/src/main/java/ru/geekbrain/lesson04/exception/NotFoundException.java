package ru.geekbrain.lesson04.exception;

/**
 * Выдать исключение!
 */
public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}

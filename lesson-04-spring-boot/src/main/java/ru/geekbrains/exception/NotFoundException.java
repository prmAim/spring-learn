package ru.geekbrains.exception;

/**
 * Выдать исключение!
 */
public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}

package com.intellias.intellistart.interviewplanning.exceptions;

import java.time.DayOfWeek;

/**
 * InvalidDayOfWeekException class.
 */
public class InvalidDayOfWeekException extends ApplicationErrorException {

  public static final String ERROR_MESSAGE = "Invalid day of week: %s";

  /**
   * constructor.
   *
   * @param errorMessage error message
   */
  public InvalidDayOfWeekException(String errorMessage) {
    super(ErrorCode.INVALID_DAY_OF_WEEK, errorMessage);
  }

  /**
   * constructor.
   *
   * @param dayOfWeek day of week
   */
  public InvalidDayOfWeekException(DayOfWeek dayOfWeek) {
    this(String.format(ERROR_MESSAGE, dayOfWeek.toString()));
  }

}

package com.intellias.intellistart.interviewplanning.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intellias.intellistart.interviewplanning.exceptions.ApplicationErrorException.ErrorCode;
import java.time.DayOfWeek;
import org.junit.jupiter.api.Test;

class InvalidDayOfWeekExceptionTest {

  private static InvalidDayOfWeekException exception;

  @Test
  void testConstructorString() {
    exception = new InvalidDayOfWeekException("test");
    try {
      throw exception;
    } catch (InvalidDayOfWeekException e) {
      assertEquals("test", e.getMessage());
      assertEquals(ErrorCode.INVALID_DAY_OF_WEEK.code, e.getErrorCode());
    }
  }

  @Test
  void testConstructorLong() {
    exception = new InvalidDayOfWeekException(DayOfWeek.FRIDAY);
    try {
      throw exception;
    } catch (InvalidDayOfWeekException e) {
      assertEquals(ErrorCode.INVALID_DAY_OF_WEEK.code, e.getErrorCode());
      assertEquals(
          String.format(InvalidDayOfWeekException.ERROR_MESSAGE, DayOfWeek.FRIDAY),
          e.getMessage());
    }
  }

}

package com.intellias.intellistart.interviewplanning.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intellias.intellistart.interviewplanning.exceptions.ApplicationErrorException.ErrorCode;
import org.junit.jupiter.api.Test;

class UpdateSlotNotAllowedExceptionTest {

  private static final UpdateSlotNotAllowedException updateSlotNotAllowedException =
      new UpdateSlotNotAllowedException(1L);

  void throwException() {
    throw updateSlotNotAllowedException;
  }

  @Test
  void test() {
    try {
      throwException();
    } catch (UpdateSlotNotAllowedException e) {
      assertEquals(e.getMessage(),
          String.format(UpdateSlotNotAllowedException.ERROR_MESSAGE, 1L));
      assertEquals(e.getErrorCode(),
          ErrorCode.UPDATE_SLOT_NOT_ALLOWED.code);
    }
  }
}

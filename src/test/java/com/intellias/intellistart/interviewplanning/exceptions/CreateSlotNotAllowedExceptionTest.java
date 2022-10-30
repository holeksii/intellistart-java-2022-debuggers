package com.intellias.intellistart.interviewplanning.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intellias.intellistart.interviewplanning.exceptions.ApplicationErrorException.ErrorCode;
import org.junit.jupiter.api.Test;

class CreateSlotNotAllowedExceptionTest {

  private static final CreateSlotNotAllowedException createSlotNotAllowedException =
      new CreateSlotNotAllowedException(1L);

  void throwException() {
    throw createSlotNotAllowedException;
  }

  @Test
  void test() {
    try {
      throwException();
    } catch (CreateSlotNotAllowedException e) {
      assertEquals(e.getMessage(),
          String.format(CreateSlotNotAllowedException.ERROR_MESSAGE, 1L));
      assertEquals(e.getErrorCode(),
          ErrorCode.CREATE_SLOT_NOT_ALLOWED.code);
    }
  }
}

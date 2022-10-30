package com.intellias.intellistart.interviewplanning.exceptions;

/**
 * Exception for crate slot.
 */
public class CreateSlotNotAllowedException extends ApplicationErrorException {

  public static final String ERROR_MESSAGE = "Create slot with id=%d not allowed";

  /**
   * EditSlotNotAllowedException Constructor.
   *
   * @param errorMessage message
   */
  public CreateSlotNotAllowedException(String errorMessage) {
    super(ErrorCode.CREATE_SLOT_NOT_ALLOWED, errorMessage);
  }

  /**
   * EditSlotNotAllowedException Constructor.
   *
   * @param slotId slot id
   */
  public CreateSlotNotAllowedException(Long slotId) {
    this(String.format(ERROR_MESSAGE, slotId));
  }

}

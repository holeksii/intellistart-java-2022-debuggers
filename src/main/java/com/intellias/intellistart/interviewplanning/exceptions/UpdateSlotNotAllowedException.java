package com.intellias.intellistart.interviewplanning.exceptions;

/**
 * Exception for editing slot.
 */
public class UpdateSlotNotAllowedException extends ApplicationErrorException {

  public static final String ERROR_MESSAGE = "Edit slot with id=%d not allowed";

  /**
   * EditSlotNotAllowedException Constructor.
   *
   * @param errorMessage message
   */
  public UpdateSlotNotAllowedException(String errorMessage) {
    super(ErrorCode.UPDATE_SLOT_NOT_ALLOWED, errorMessage);
  }

  /**
   * EditSlotNotAllowedException Constructor.
   *
   * @param slotId slot id
   */
  public UpdateSlotNotAllowedException(Long slotId) {
    this(String.format(ERROR_MESSAGE, slotId));
  }

}

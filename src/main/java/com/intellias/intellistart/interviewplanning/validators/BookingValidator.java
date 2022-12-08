package com.intellias.intellistart.interviewplanning.validators;

import com.intellias.intellistart.interviewplanning.controllers.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.exceptions.ApplicationErrorException;
import com.intellias.intellistart.interviewplanning.exceptions.ApplicationErrorException.ErrorCode;
import com.intellias.intellistart.interviewplanning.exceptions.InvalidInputException;
import com.intellias.intellistart.interviewplanning.models.Booking;

/**
 * Booking validator class.
 */
public class BookingValidator {

  /**
   * Method to validate booking.
   *
   * @param booking             booking
   */
  public void validateSlotOverlapping(Booking booking) {
    var interviewerTimeSlot = booking.getInterviewerSlot();
    var candidateTimeSlot = booking.getCandidateSlot();

    if (!(booking.getFrom().isAfter(interviewerTimeSlot.getFrom())
        || booking.getFrom().equals(interviewerTimeSlot.getFrom())
        && booking.getFrom().isAfter(candidateTimeSlot.getFrom())
        || booking.getFrom().equals(candidateTimeSlot.getFrom())
        && booking.getTo().isBefore(interviewerTimeSlot.getTo())
        || booking.getTo().equals(interviewerTimeSlot.getTo())
        && booking.getTo().isBefore(candidateTimeSlot.getTo())
        || booking.getTo().equals(candidateTimeSlot.getTo()))) {
      throw new ApplicationErrorException(ErrorCode.INVALID_BOUNDARIES,
          "booking time boundaries not overlapping with slots boundaries");
    }
  }

  public void validateTextFieldsLength(BookingDto bookingDto) {
    if (bookingDto.getSubject().length() > Booking.MAX_SUBJECT_LENGTH) {
      throw InvalidInputException.subject(bookingDto.getSubject().length());
    }
    if (bookingDto.getDescription().length() > Booking.MAX_DESCRIPTION_LENGTH) {
      throw InvalidInputException.description(bookingDto.getDescription().length());
    }
  }

}

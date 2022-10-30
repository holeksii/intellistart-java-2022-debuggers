package com.intellias.intellistart.interviewplanning.validators;

import com.intellias.intellistart.interviewplanning.exceptions.CannotCreateOrUpdateSlotException;
import com.intellias.intellistart.interviewplanning.exceptions.InvalidDayOfWeekException;
import com.intellias.intellistart.interviewplanning.models.InterviewerTimeSlot;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * InterviewerSlotValidate class. Used to validate interviewer time slot before creating or
 * updating.
 */
public class InterviewerSlotValidate {

  private static final String WEEKEND_MESSAGE = "Cannot %s slot on weekend";
  private static final String NEXT_WEEK_MESSAGE = "Can %s slot only for next week";

  private InterviewerSlotValidate() {
  }

  /**
   * Checks if slot is allowed to be created by interviewer.
   *
   * @param interviewerTimeSlot slot to validate
   * @throws InvalidDayOfWeekException if creating slot on weekend
   * @throws CannotCreateOrUpdateSlotException if creating slot not for next week
   */
  public static void validateSlotToBeCreated(InterviewerTimeSlot interviewerTimeSlot) {
    checkAction(interviewerTimeSlot, "create");
  }

  /**
   * Checks if slot is allowed to be updated by interviewer.
   *
   * @param interviewerTimeSlot slot to validate
   * @throws InvalidDayOfWeekException if updating slot on weekend
   * @throws CannotCreateOrUpdateSlotException if updating slot not for next week
   */
  public static void validateSlotToBeUpdated(InterviewerTimeSlot interviewerTimeSlot) {
    checkAction(interviewerTimeSlot, "edit");
  }

  private static void checkAction(InterviewerTimeSlot interviewerTimeSlot, String action) {
    var now = LocalDate.now();
    if (isWeekend(now.getDayOfWeek())) {
      throw new InvalidDayOfWeekException(
          String.format(WEEKEND_MESSAGE, action));
    } else if (interviewerTimeSlot.getWeekNum() != WeekService.getNextWeekNum()) {
      throw new CannotCreateOrUpdateSlotException(
          String.format(NEXT_WEEK_MESSAGE, action));
    }
  }

  private static boolean isWeekend(DayOfWeek dayOfWeek) {
    return dayOfWeek.getValue() > DayOfWeek.FRIDAY.getValue();
  }

}

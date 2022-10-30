package com.intellias.intellistart.interviewplanning.validators;

import com.intellias.intellistart.interviewplanning.exceptions.CreateSlotNotAllowedException;
import com.intellias.intellistart.interviewplanning.exceptions.UpdateSlotNotAllowedException;
import com.intellias.intellistart.interviewplanning.models.InterviewerTimeSlot;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * InterviewerSlotValidate class. Used to validate interviewer time slot before creating or
 * updating.
 */
public class InterviewerSlotValidate {

  private InterviewerSlotValidate() {
  }

  /**
   * Checks if slot is allowed to edit by interviewer.
   *
   * @param interviewerTimeSlot slot to validate
   * @throws UpdateSlotNotAllowedException if slot are not allowed to edit
   */
  public static void validateSlotToBeUpdated(InterviewerTimeSlot interviewerTimeSlot) {
    if (!(nowIsByEndOfFriday()
        && interviewerTimeSlot.getWeekNum() == WeekService.getNextWeekNum())) {
      throw new UpdateSlotNotAllowedException(interviewerTimeSlot.getId());
    }
  }

  /**
   * Checks if slot is allowed to create by interviewer.
   *
   * @param interviewerTimeSlot slot to validate
   * @throws CreateSlotNotAllowedException if slot are not allowed to create
   */
  public static void validateSlotToBeCreated(InterviewerTimeSlot interviewerTimeSlot) {
    if (!(nowIsByEndOfFriday()
        && interviewerTimeSlot.getWeekNum() == WeekService.getNextWeekNum())) {
      throw new CreateSlotNotAllowedException(interviewerTimeSlot.getId());
    }
  }

  private static boolean nowIsByEndOfFriday() {
    var now = LocalDateTime.now();
    return isTimeByEndOfDay(now.toLocalTime()) && isDayByFriday(now.getDayOfWeek());
  }

  private static boolean isDayByFriday(DayOfWeek dayOfWeek) {
    return dayOfWeek.getValue() <= DayOfWeek.FRIDAY.getValue();
  }

  private static boolean isTimeByEndOfDay(LocalTime time) {
    var endOfDay = LocalTime.of(23, 59, 59);
    return time.isBefore(endOfDay)
        || time.equals(endOfDay);
  }

}

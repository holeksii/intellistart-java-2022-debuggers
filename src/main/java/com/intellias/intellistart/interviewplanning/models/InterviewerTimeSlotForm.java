package com.intellias.intellistart.interviewplanning.models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.Data;

/**
 * Slot form.
 */
@Data
public class InterviewerTimeSlotForm {

  private DayOfWeek dayOfWeek;
  private LocalTime from;
  private LocalTime to;
  private int weekNum;

  /**
   * Constructor.
   *
   * @param dayOfWeek day
   * @param from      start time
   * @param to        end time
   */
  public InterviewerTimeSlotForm(DayOfWeek dayOfWeek, LocalTime from, LocalTime to, int weekNum) {
    this.dayOfWeek = dayOfWeek;
    this.from = from;
    this.to = to;
    this.weekNum = weekNum;
  }

  /**
   * Builder.
   */
  public static class TimeSlotFormBuilder {

    private DayOfWeek dayOfWeek;
    private LocalTime from;
    private LocalTime to;
    private int weekNum;

    public TimeSlotFormBuilder from(String from) {
      this.from = LocalTime.parse(from);
      return this;
    }

    public TimeSlotFormBuilder to(String to) {
      this.to = LocalTime.parse(to);
      return this;
    }

    public TimeSlotFormBuilder dayOfWeek(String day) {
      this.dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
      return this;
    }

    public TimeSlotFormBuilder weekNum(int weekNum) {
      this.weekNum = weekNum;
      return this;
    }

    public InterviewerTimeSlotForm build() {
      return new InterviewerTimeSlotForm(dayOfWeek, from, to, weekNum);
    }

  }

  public static TimeSlotFormBuilder builder() {
    return new TimeSlotFormBuilder();
  }

}


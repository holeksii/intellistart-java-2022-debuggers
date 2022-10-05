package com.intellias.intellistart.interviewplanning.models;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

/**
 * Slot form.
 */
@Data
public class CandidateTimeSlotForm {

  private LocalDate date;
  private LocalTime from;
  private LocalTime to;

  /**
   * Constructor.
   *
   * @param date date
   * @param from start time
   * @param to   end time
   */
  public CandidateTimeSlotForm(LocalDate date, LocalTime from, LocalTime to) {
    this.date = date;
    this.from = from;
    this.to = to;
  }

  /**
   * Builder.
   */
  public static class TimeSlotFormBuilder {

    private LocalDate date;
    private LocalTime from;
    private LocalTime to;

    public TimeSlotFormBuilder from(String from) {
      this.from = LocalTime.parse(from);
      return this;
    }

    public TimeSlotFormBuilder to(String to) {
      this.to = LocalTime.parse(to);
      return this;
    }

    public TimeSlotFormBuilder date(String date) {
      this.date = LocalDate.parse(date);
      return this;
    }


    public CandidateTimeSlotForm build() {
      return new CandidateTimeSlotForm(date, from, to);
    }

  }

  public static TimeSlotFormBuilder builder() {
    return new TimeSlotFormBuilder();
  }

}


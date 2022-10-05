package com.intellias.intellistart.interviewplanning.models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

/**
 * Interview Time slot.
 */
@Entity
@Data
public class InterviewerTimeSlot {

  @Id
  private Long id;
  @Column(name = "from_time")
  private LocalTime from;
  @Column(name = "to_time")
  private LocalTime to;
  private DayOfWeek dayOfWeek;
  private int weekNum;


  /**
   * Constructor.
   *
   * @param form form
   */
  public InterviewerTimeSlot(InterviewerTimeSlotForm form) {
    from = form.getFrom();
    to = form.getTo();
    dayOfWeek = form.getDayOfWeek();
  }

  public InterviewerTimeSlot() {
  }
}

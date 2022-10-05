package com.intellias.intellistart.interviewplanning.models;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

/**
 * Candidate time slot.
 */
@Entity
@Data
public class CandidateTimeSlot {

  @Id
  private Long id;
  private LocalTime from;
  private LocalTime to;
  private LocalDate date;

  /**
   * Constructor.
   *
   * @param form form
   */
  public CandidateTimeSlot(CandidateTimeSlotForm form) {
    from = form.getFrom();
    to = form.getTo();
    date = form.getDate();
  }

  public CandidateTimeSlot() {
  }
}

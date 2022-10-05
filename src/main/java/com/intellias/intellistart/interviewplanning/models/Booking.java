package com.intellias.intellistart.interviewplanning.models;

import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Data;

/**
 * Booking.
 */
@Entity
@Data
public class Booking {

  @Id
  @SequenceGenerator(name = "booking_seq", sequenceName = "booking_sequence", allocationSize = 5)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_seq")
  @Column(nullable = false)
  private Long id;
  @Column(name = "from_time")
  private LocalTime from;
  @Column(name = "to_time")
  private LocalTime to;
  private BookingStatus status;
  @ManyToOne
  private InterviewerTimeSlot candidateSlot;

  @ManyToOne
  private InterviewerTimeSlot interviewerSlot;

  /**
   * Constructor.
   *
   * @param from            start time
   * @param to              end time
   * @param candidateSlot   candidate slot
   * @param interviewerSlot interviewer slot
   */
  public Booking(LocalTime from, LocalTime to, InterviewerTimeSlot candidateSlot,
                 InterviewerTimeSlot interviewerSlot) {
    //TODO: better way to create booking
    this.from = from;
    this.to = to;
    this.candidateSlot = candidateSlot;
    this.interviewerSlot = interviewerSlot;
    status = BookingStatus.NEW;
  }

  /**
   * Constructor.
   *
   * @param candidateSlot   candidate slot
   * @param interviewerSlot interviewer slot
   */
  public Booking(InterviewerTimeSlot candidateSlot, InterviewerTimeSlot interviewerSlot) {
    this.candidateSlot = candidateSlot;
    this.interviewerSlot = interviewerSlot;
    status = BookingStatus.NEW;
  }

  public Booking() {
  }

  /**
   * Booking statuses. Default is NEW.
   */
  public enum BookingStatus {
    NEW, CHANGED, PRE_BOOKED, BOOKED, DELETED
  }
}

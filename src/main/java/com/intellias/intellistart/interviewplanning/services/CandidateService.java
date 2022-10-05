package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.CandidateTimeSlot;
import com.intellias.intellistart.interviewplanning.models.CandidateTimeSlotForm;
import com.intellias.intellistart.interviewplanning.models.UserWithSlots;

/**
 * Candidate service.
 */
public class CandidateService extends UserWithSlots {

  /**
   * Crete slot for candidate.
   *
   * @param from start time
   * @param to   end time
   * @param date  date
   * @return slot
   */
  public static CandidateTimeSlot createSlot(String from, String to, String date) {

    return new CandidateTimeSlot(CandidateTimeSlotForm.builder()
        .from(from)
        .to(to)
        .date(date)
        .build());
  }
}

package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.controllers.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.DashboardDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.DayDashboardDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.exceptions.ApplicationErrorException;
import com.intellias.intellistart.interviewplanning.exceptions.ApplicationErrorException.ErrorCode;
import com.intellias.intellistart.interviewplanning.exceptions.NotFoundException;
import com.intellias.intellistart.interviewplanning.models.BookingImpl;
import com.intellias.intellistart.interviewplanning.models.CandidateTimeSlotImpl;
import com.intellias.intellistart.interviewplanning.models.InterviewerTimeSlotImpl;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.User.UserRole;
import com.intellias.intellistart.interviewplanning.repositories.BookingRepository;
import com.intellias.intellistart.interviewplanning.repositories.CandidateTimeSlotRepository;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerTimeSlotRepository;
import com.intellias.intellistart.interviewplanning.repositories.UserRepository;
import com.intellias.intellistart.interviewplanning.services.interfaces.WeekService;
import com.intellias.intellistart.interviewplanning.utils.mappers.BookingMapper;
import com.intellias.intellistart.interviewplanning.utils.mappers.CandidateSlotMapper;
import com.intellias.intellistart.interviewplanning.utils.mappers.InterviewerSlotMapper;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Coordinator service.
 */
@Service
@RequiredArgsConstructor
public class CoordinatorService {

  private final WeekService weekService;
  private final InterviewerTimeSlotRepository interviewerTimeSlotRepository;
  private final CandidateTimeSlotRepository candidateTimeSlotRepository;
  private final BookingRepository bookingRepository;
  private final UserRepository userRepository;

  /**
   * Returns week dashboard with time slots and bookings by a specified number of week.
   *
   * @param weekNum number of the week
   * @return dashboard with time slots and bookings for the week
   */
  public DashboardDto getWeekDashboard(int weekNum) {
    Set<DayDashboardDto> set = new TreeSet<>(Comparator.comparing(DayDashboardDto::getDate));
    set.add(getDayDashboard(weekNum, DayOfWeek.MONDAY));
    set.add(getDayDashboard(weekNum, DayOfWeek.TUESDAY));
    set.add(getDayDashboard(weekNum, DayOfWeek.WEDNESDAY));
    set.add(getDayDashboard(weekNum, DayOfWeek.THURSDAY));
    set.add(getDayDashboard(weekNum, DayOfWeek.FRIDAY));
    return new DashboardDto(set);

  }

  /**
   * Returns day dashboard with time slots and bookings by a specified week number and day of week.
   *
   * @param weekNum number of the week
   * @param day     day of the week
   * @return dashboard with time slots and bookings for the day
   */
  public DayDashboardDto getDayDashboard(int weekNum, DayOfWeek day) {
    LocalDate date = weekService.getDateByWeekNumAndDayOfWeek(weekNum, day);

    List<InterviewerTimeSlotImpl> interviewerSlots = interviewerTimeSlotRepository
        .findByWeekNumAndDayOfWeek(weekNum, day);
    List<CandidateTimeSlotImpl> candidateSlots = candidateTimeSlotRepository.findByDate(date);
    List<BookingImpl> bookings = bookingRepository.findByCandidateSlotDate(date);

    return DayDashboardDto.builder()
        .date(date)
        .dayOfWeek(day.getDisplayName(TextStyle.SHORT, Locale.US))
        .interviewerSlots(getInterviewerSlotsWithBookings(interviewerSlots))
        .candidateSlots(getCandidateSlotsWithBookings(candidateSlots))
        .bookings(getBookingMap(bookings))
        .build();
  }

  /**
   * Returns interviewer slots with bookings.
   *
   * @param slots interviewer time slots
   * @return a list of interviewer time slots with bookings
   */
  public List<InterviewerSlotDto> getInterviewerSlotsWithBookings(List<InterviewerTimeSlotImpl> slots) {
    return slots.stream()
        .map(slot -> InterviewerSlotMapper.mapToDtoWithBookings(slot,
            bookingRepository.findByInterviewerSlot(slot)))
        .sorted(Comparator.comparing(InterviewerSlotDto::getFrom))
        .collect(Collectors.toList());
  }

  /**
   * Returns candidate slots with bookings.
   *
   * @param slots candidate time slots
   * @return a list of candidate time slots with bookings
   */
  public List<CandidateSlotDto> getCandidateSlotsWithBookings(List<CandidateTimeSlotImpl> slots) {
    return slots.stream()
        .map(slot -> CandidateSlotMapper.mapToDtoWithBookings(slot,
            bookingRepository.findByCandidateSlot(slot)))
        .sorted(Comparator.comparing(CandidateSlotDto::getFrom))
        .collect(Collectors.toList());
  }

  /**
   * Grouping the bookings into a map.
   *
   * @param bookings list of bookings
   * @return map of bookings as map bookingId bookingData
   */
  public Map<Long, BookingDto> getBookingMap(List<BookingImpl> bookings) {
    return bookings.stream()
        .map(BookingMapper::mapToDto)
        .collect(Collectors.toMap(BookingDto::getId, Function.identity()));
  }

  /**
   * Grant user the interviewer role by email.
   *
   * @param email                   email of the user
   * @param currentCoordinatorEmail email of the current coordinator
   * @return user with the granted interviewer role
   * @throws ApplicationErrorException if coordinator grant himself
   */
  public User grantInterviewerRole(String email, String currentCoordinatorEmail) {
    if (email.equals(currentCoordinatorEmail)) {
      throw new ApplicationErrorException(ErrorCode.SELF_ROLE_REVOKING);
    }
    User user = userRepository.findByEmail(email)
        .orElseGet(() -> new User(email, UserRole.INTERVIEWER));
    user.setRole(UserRole.INTERVIEWER);
    return userRepository.save(user);
  }

  /**
   * Grant user the coordinator role by email.
   *
   * @param email user email
   * @return user with the granted coordinator role
   * @throws ApplicationErrorException if user is interviewer and has active slots
   */
  public User grantCoordinatorRole(String email) {
    User user = userRepository.findByEmail(email)
        .orElseGet(() -> new User(email, UserRole.COORDINATOR));
    if (user.getRole() == UserRole.INTERVIEWER && hasActiveSlot(user)) {
      throw new ApplicationErrorException(ErrorCode.REVOKE_USER_WITH_SLOT);
    }
    user.setRole(UserRole.COORDINATOR);
    return userRepository.save(user);
  }

  /**
   * Revoke the interviewer role by user id.
   *
   * @param id id of the user whose role will be revoked
   * @return user with revoked interviewer role
   * @throws NotFoundException         if interviewer with the specified id is not found
   * @throws ApplicationErrorException if interviewer has active slots
   */
  public User revokeInterviewerRole(Long id) {
    User user = userRepository.findById(id).orElseThrow(() -> NotFoundException.user(id));
    if (user.getRole() != UserRole.INTERVIEWER) {
      throw NotFoundException.interviewer(id);
    }
    if (hasActiveSlot(user)) {
      throw new ApplicationErrorException(ErrorCode.REVOKE_USER_WITH_SLOT);
    }
    user.setRole(UserRole.CANDIDATE);
    return userRepository.save(user);
  }

  /**
   * Revoke the coordinator role by user id.
   *
   * @param id                   id of the user whose role will be revoked
   * @param currentCoordinatorId id of the current coordinator
   * @return user with revoked coordinator role
   * @throws NotFoundException         if coordinator with the specified id is not found
   * @throws ApplicationErrorException if coordinator revoke himself
   */
  public User revokeCoordinatorRole(Long id, Long currentCoordinatorId) {
    if (id.equals(currentCoordinatorId)) {
      throw new ApplicationErrorException(ErrorCode.SELF_ROLE_REVOKING);
    }
    User user = userRepository.findById(id).orElseThrow(() -> NotFoundException.user(id));
    if (user.getRole() != UserRole.COORDINATOR) {
      throw NotFoundException.coordinator(id);
    }
    user.setRole(UserRole.CANDIDATE);
    return userRepository.save(user);
  }

  /**
   * Provides all users with the specified role.
   *
   * @param role user role
   * @return a list of users with the specified role
   */
  public List<User> getUsersWithRole(UserRole role) {
    return userRepository.findByRole(role);
  }

  /**
   * Checks if the user has slots in the future.
   *
   * @param user interviewer
   * @return true if user has active slots, otherwise - false
   */
  private boolean hasActiveSlot(User user) {
    List<InterviewerTimeSlotImpl> slots = interviewerTimeSlotRepository.findByInterviewer(user);
    for (InterviewerTimeSlotImpl slot : slots) {
      if (weekService.getDateByWeekNumAndDayOfWeek(slot.getWeekNum(), slot.getDayOfWeek())
          .atTime(slot.getFrom())
          .isAfter(weekService.getCurrentDateTime())) {
        return true;
      }
    }
    return false;
  }
}

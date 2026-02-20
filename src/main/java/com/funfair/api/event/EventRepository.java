package com.funfair.api.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventDetails, String> {

	EventDetails findByEventId(String eventId);

	List<EventDetails> findByOrganizerId(String organizerId);

	List<EventDetails> findByEventStartDateTimeGreaterThanEqualAndEventEndDateTimeLessThanEqual(LocalDate startDate,
			LocalDate endDate);

	List<EventDetails> findByEventStartDateTimeLessThanEqualAndEventEndDateTimeGreaterThanEqual(LocalDateTime endOfWeek,
			LocalDateTime now);

	List<EventDetails> findByEventStartDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

	List<EventDetails> findByIsActiveTrue();

	List<EventDetails> findByEventCatagory(EventCatagoryEnum catagoryEnum);
 
	List<EventDetails> findByIsActiveTrueAndIsPrivateEventFalseAndIsEventInDraftFalseAndIsPostEventFalse();

	EventDetails findByEventIdAndEventEndDateTimeAfter(String eventId, LocalDateTime now);

	List<EventDetails> findByEventStartDateTimeAfterOrderByEventStartDateTimeAsc(LocalDateTime now);

}
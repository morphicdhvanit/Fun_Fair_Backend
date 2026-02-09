package com.funfair.api.event;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventDetails, String> {

	EventDetails findByEventId(String eventId);

	List<EventDetails> findByOrganizerId(String organizerId);



	List<EventDetails> findByEventStartDateTimeLessThanEqualAndEventEndDateTimeGreaterThanEqual(LocalDateTime endOfWeek,
			LocalDateTime now);

}